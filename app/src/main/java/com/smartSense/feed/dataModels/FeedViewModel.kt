package com.smartSense.feed.dataModels

import androidx.lifecycle.ViewModel
import com.smartSense.feed.R

/**
 * Created by Mala Ruparel
 */
class FeedViewModel : ViewModel() {

    fun getUser() = arrayListOf<User>().apply {

        add(
            User(
                userName = "Mala Ruparel",
                userProfile = R.drawable.me,
                imgUrl = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Cristiano_Ronaldo_2018.jpg"
            )
        )
    }


    fun getPosts(): Feed {
        val feed = Feed()
        feed.data = arrayListOf<FeedData>().apply {

            add(
                FeedData(
                    type =2,
                    data = Data(
                        authorName = "Mala Ruparel",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        feedVideo ="https://firebasestorage.googleapis.com/v0/b/videolist-fb641.appspot.com/o/video_0.mp4?alt=media&token=cd0fa6be-4bc3-43d0-8a59-635c57b7c7c6",
                        dimension ="640:640"


                    )

                )
            )


            add(
                FeedData(
                    type = 1,
                    data = Data(
                        authorName = "Mala Ruparel",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        feedImageUri = arrayListOf<FeedImage>().apply {
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1/5616/3744.jpg?hmac=kKHwwU8s46oNettHKwJ24qOlIAsWN9d2TtsXDoCWWsQ")
                            )
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1/5616/3744.jpg?hmac=kKHwwU8s46oNettHKwJ24qOlIAsWN9d2TtsXDoCWWsQ")
                            )
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1/5616/3744.jpg?hmac=kKHwwU8s46oNettHKwJ24qOlIAsWN9d2TtsXDoCWWsQ")
                            )


                        }

                    )

                )
            )
            add(
                FeedData(
                    type = 1,
                    data = Data(
                        authorName = "Mala Ruparel",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        feedImageUri = arrayListOf<FeedImage>().apply {
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1025/4951/3301.jpg?hmac=_aGh5AtoOChip_iaMo8ZvvytfEojcgqbCH7dzaz-H8Y")
                            )
                        }

                    )

                )
            )
            add(
                FeedData(
                    type =2,
                    data = Data(
                        authorName = "Mala Ruparel",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        feedVideo ="https://firebasestorage.googleapis.com/v0/b/videolist-fb641.appspot.com/o/video_6.mp4?alt=media&token=2787c00c-93f8-43b9-876a-2511197050bc",
                        dimension ="640:800"


                    )

                )
            )
            add(
                FeedData(
                    type = 0,
                    data = Data(
                        authorName = "Mala Ruparel",
                        feedText = "having an incentive or a strong desire to do well or succeed in some pursuit a motivated employee Courses are being offered on college campuses for those who are highly motivated but who without such help often drop out and are lost to society and themselves",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                    )
                )

            )
            add(
                FeedData(
                    type = 1,
                    data = Data(
                        authorName = "Mala Ruparel",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        feedImageUri = arrayListOf<FeedImage>().apply {
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1024/1920/1280.jpg?hmac=-PIpG7j_fRwN8Qtfnsc3M8-kC3yb0XYOBfVzlPSuVII")
                            )
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1018/3914/2935.jpg?hmac=3N43cQcvTE8NItexePvXvYBrAoGbRssNMpuvuWlwMKg")
                            )
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/0/5616/3744.jpg?hmac=3GAAioiQziMGEtLbfrdbcoenXoWAW-zlyEAMkfEdBzQ")
                            )
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1000/5626/3635.jpg?hmac=qWh065Fr_M8Oa3sNsdDL8ngWXv2Jb-EE49ZIn6c0P-g")
                            )
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/0/5616/3744.jpg?hmac=3GAAioiQziMGEtLbfrdbcoenXoWAW-zlyEAMkfEdBzQ")
                            )
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1000/5626/3635.jpg?hmac=qWh065Fr_M8Oa3sNsdDL8ngWXv2Jb-EE49ZIn6c0P-g")
                            )

                        }

                    )

                )
            )

            add(
                FeedData(
                    type = 0,
                    data = Data(
                        authorName = "Mala Ruparel",
                        feedText = "having an incentive or a strong desire to do well or succeed in some pursuit a motivated employee Courses are being offered on college campuses for those who are highly motivated but who without such help often drop out and are lost to society and themselves",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                    )

                )
            )
            add(
                FeedData(
                    type = 0,
                    data = Data(
                        authorName = "Mala Ruparel",
                        feedText = "having an incentive or a strong desire to do well or succeed in some pursuit a motivated employee Courses are being offered on college campuses for those who are highly motivated but who without such help often drop out and are lost to society and themselves",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                    )

                )
            )

            add(
                FeedData(
                    type = 1,
                    data = Data(
                        authorName = "Mala Ruparel",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        feedImageUri = arrayListOf<FeedImage>().apply {
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1033/2048/1365.jpg?hmac=zEuPfX7t6U866nzXjWF41bf-uxkKOnf1dDrHXmhcK-Q")
                            )
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1038/3914/5863.jpg?hmac=SGtXryWDkn_eVQdA1NjYrikcsrIfcfS4SFYHo4lCpkQ")
                            )
                        }

                    )

                )
            )
            add(
                FeedData(
                    type = 1,
                    data = Data(
                        authorName = "Mala Ruparel",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        feedImageUri = arrayListOf<FeedImage>().apply {
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1039/6945/4635.jpg?hmac=tdgHDygif2JPCTFMM7KcuOAbwEU11aucKJ8eWcGD9_Q")
                            )
                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1048/5616/3744.jpg?hmac=N5TZKe4gtmf4hU8xRs-zbS4diYiO009Jni7n50609zk")
                            )
                        }

                    )

                )
            )

            add(
                FeedData(
                    type = 1,
                    data = Data(
                        authorName = "Mala Ruparel",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        feedImageUri = arrayListOf<FeedImage>().apply {

                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/1018/3914/2935.jpg?hmac=3N43cQcvTE8NItexePvXvYBrAoGbRssNMpuvuWlwMKg")
                            )
                        }

                    )

                )
            )
            add(
                FeedData(
                    type = 0,
                    data = Data(
                        authorName = "Mala Ruparel",
                        feedText = "having an incentive or a strong desire to do well or succeed in some pursuit a motivated employee Courses are being offered on college campuses for those who are highly motivated but who without such help often drop out and are lost to society and themselves",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                    )

                )
            )
            add(
                FeedData(
                    type = 2,
                    data = Data(
                        authorName = "Mala Ruparel",
                        feedVideo = "https://firebasestorage.googleapis.com/v0/b/videolist-fb641.appspot.com/o/video_9.mp4?alt=media&token=7eac8d4b-d93f-4f5e-a8d8-ae58ae75bcc9",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        dimension ="640:800"
                    )

                )
            )
            add(
                FeedData(
                    type = 2,
                    data = Data(
                        authorName = "Mala Ruparel",
                        feedVideo = "https://firebasestorage.googleapis.com/v0/b/videolist-fb641.appspot.com/o/video_8.mp4?alt=media&token=90a85c8c-f65d-4a38-bdc6-51600f787d02",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        dimension ="640:800"
                    )

                )
            )
            add(
                FeedData(
                    type = 1,
                    data = Data(
                        authorName = "Mala Ruparel",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        feedImageUri = arrayListOf<FeedImage>().apply {

                            add(
                                FeedImage(imageUri = "https://i.picsum.photos/id/104/3840/2160.jpg?hmac=Rv0qxBiYb65Htow4mdeDlyT5kLM23Z2cDlN53YYldZU")
                            )
                        }

                    )

                )
            )
            add(
                FeedData(
                    type = 2,
                    data = Data(
                        authorName = "Mala Ruparel",
                        feedVideo = "https://firebasestorage.googleapis.com/v0/b/videolist-fb641.appspot.com/o/video_8.mp4?alt=media&token=90a85c8c-f65d-4a38-bdc6-51600f787d02",
                        authorPhoto = R.drawable.me,
                        description = "it is simply dummy text",
                        dimension ="640:800"
                    )

                )
            )
        }
        return feed

    }

}