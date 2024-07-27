package ir.hoseinahmadi.myapplication.data.model

data class CourseSection(
    val id: Int=-0,
    val title: String="",
    val videoUri:String="",
)

data class CourseItem(
    val id:Int=0,
    val title:String="",
    val image:String="",
    val introductionVideo:String = "",
    val section:List<CourseSection> = emptyList(),
)
