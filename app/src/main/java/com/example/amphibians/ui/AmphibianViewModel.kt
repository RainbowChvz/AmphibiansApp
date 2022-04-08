/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibianApi
import kotlinx.coroutines.launch

enum class AmphibianApiStatus {LOADING, ERROR, DONE}

class AmphibianViewModel : ViewModel() {

    // TODO: Create properties to represent MutableLiveData and LiveData for the API status
    // TODO: Create properties to represent MutableLiveData and LiveData for a list of amphibian objects
    private val _status = MutableLiveData<AmphibianApiStatus>()
    private val _amphibians = MutableLiveData<List<Amphibian>>()
    private val _amphibian = MutableLiveData<Amphibian>()

    val status: LiveData<AmphibianApiStatus> = _status
    val amphibians: LiveData<List<Amphibian>> = _amphibians
    val amphibian: LiveData<Amphibian> = _amphibian

    // TODO: Create properties to represent MutableLiveData and LiveData for a single amphibian object.
    //  This will be used to display the details of an amphibian when a list item is clicked

    // TODO: Create a function that gets a list of amphibians from the api service and sets the
    //  status via a Coroutine

    fun onAmphibianClicked(amphibian: Amphibian) {
        // TODO: Set the amphibian object
        _amphibian.value = amphibian
    }

    fun getAmphibianList() {

        _status.value = AmphibianApiStatus.LOADING
        viewModelScope.launch {
            try {
                _amphibians.value = AmphibianApi.retrofitService.getAmphibians()
                _status.value = AmphibianApiStatus.DONE
            } catch (e: Exception) {
                _amphibians.value = listOf()
                _status.value = AmphibianApiStatus.ERROR
            }
        }
    }
}
