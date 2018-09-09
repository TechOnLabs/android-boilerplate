package com.techonlabs.androidboilerplate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.techonlabs.androidboilerplate.AnimeModel
import com.techonlabs.androidboilerplate.R
import com.techonlabs.androidboilerplate.VoiceActorEntity
import com.techonlabs.androidboilerplate.databinding.AnimeFragmentBinding
import com.techonlabs.androidboilerplate.datalayer.local.AnimeDao
import com.techonlabs.androidboilerplate.datalayer.network.ApiInterface
import com.techonlabs.androidboilerplate.datalayer.network.NetworkCallback
import com.techonlabs.androidboilerplate.utils.RequestState
import com.techonlabs.androidboilerplate.utils.baseComponents.BaseFragment
import com.techonlabs.androidboilerplate.utils.baseComponents.BaseViewModel
import com.techonlabs.androidboilerplate.utils.extensions.bindView
import com.techonlabs.androidboilerplate.utils.extensions.submitListV2
import com.techonlabs.androidboilerplate.utils.extensions.toast
import com.techonlabs.androidboilerplate.utils.recyclerView.PagedRecyclerAdapter
import com.techonlabs.androidboilerplate.utils.recyclerView.StableId
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimeFragment : BaseFragment() {
    override val vm by viewModel<AnimeVM>()
    private lateinit var binding: AnimeFragmentBinding
    override fun onCreateBaseView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container!!.bindView(R.layout.anime_fragment)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Request state can be used to show progress dialog while fetching data from network
        vm.requestState.observe(this, Observer {
            it?.let {
                when (it) {
                    RequestState.Success -> {
                        toast("List fetched")
                    }
                    RequestState.Fetching -> {
                        toast("Please wait! Loading anime list")
                    }
                    RequestState.Failed -> {
                        toast("Request failed")
                    }
                    RequestState.NetworkFail -> {
                        toast("No internet connection")
                    }
                }
            }
        })
        PagedRecyclerAdapter(mutableMapOf(VoiceActorEntity::class to R.layout.anime_cell), this).let { adapter ->
            binding.animeListView.adapter = adapter
            vm.animeList.observe(this, Observer(adapter::submitListV2))
//            vm.animeList.observe(this, Observer {
//                it.dataSource
//                adapter.submitListV2(it)
//            })
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = vm
    }

    override fun onRecyclerItemClick(obj: StableId) {
        super.onRecyclerItemClick(obj)
        if (obj is VoiceActorEntity) {
            toast("Opening Food Screen")
            navController.navigate(R.id.action_animeFragment_to_foodFragment)
        }
    }
}

class AnimeVM(private val apiInterface: ApiInterface,
              val animeDao: AnimeDao) : BaseViewModel() {

    init {
        getAnime()
        setStateFetching()
    }

    fun getAnime() {
        { apiInterface.getAnime() } callback {
            NetworkCallback<AnimeModel>().apply {
                success = {
                    setStateSuccess()
                    load {
                        it.characters.forEach {
                            animeDao.insert(it.voice_actors)
                        }
                    }

                }
                error = {
                    setStateNetworkFail()
                }
                httpError = {
                    setStateFailed()
                }
            }
        }
    }

    val animeList = getPagedList(animeDao.getPaged())
}