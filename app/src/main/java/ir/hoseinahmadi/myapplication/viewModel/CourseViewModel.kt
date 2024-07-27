package ir.hoseinahmadi.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.myapplication.data.model.CourseItemDb
import ir.hoseinahmadi.myapplication.repository.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

     fun upsertCourseItem(itemDb: CourseItemDb){
         viewModelScope.launch(Dispatchers.IO) {
             repository.upsertCourseItem(itemDb)
         }
     }

}