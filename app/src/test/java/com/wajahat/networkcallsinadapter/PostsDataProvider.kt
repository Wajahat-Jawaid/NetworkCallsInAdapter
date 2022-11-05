package com.wajahat.networkcallsinadapter

import com.wajahat.networkcallsinadapter.data.model.Author
import com.wajahat.networkcallsinadapter.data.model.Post

object PostsDataProvider {

    fun getDummyPosts(): List<Post> {
        return mutableListOf<Post>().apply {
            add(
                Post(
                    "Journeyman&#8217;s Journal",
                    "<p>A blog by third-generation woodworker Salko Safic on the art of woodworking by hand.</p>",
                    Author("Carolyn Wells", "onceuponatime66.wordpress.com"),
                    "2020-08-28T09:00:26-04:00",
                    "https://discover.wordpress.com/2020/08/28/journeymans-journal/",
                    "https://discover.files.wordpress.com/2020/08/screen-shot-2020-08-13-at-4.31.42-pm.png",
                    2
                )
            )
            add(
                Post(
                    "A Dollop of History",
                    "<p>A blog about medieval food, with recipes to bring the past to life! Fourteenth-century mushroom pasties anyone?</p>",
                    Author("Carolyn Wells", "onceuponatime66.wordpress.com"),
                    "2020-08-21T09:00:26-04:00",
                    "https://discover.wordpress.com/2020/08/21/a-dollop-of-history/",
                    "https://discover.files.wordpress.com/2020/08/screen-shot-2020-08-13-at-4.16.41-pm.png",
                    2
                )
            )
            add(
                Post(
                    "Neuroscience News",
                    "<p>Neuroscience News research articles cover neurology, psychology, AI, brain science, mental health, robotics and cognitive sciences in a free, open access magazine.</p>",
                    Author("Carolyn Wells", "onceuponatime66.wordpress.com"),
                    "2020-08-14T09:00:33-04:00",
                    "https://discover.wordpress.com/2020/08/14/neuroscience-news/",
                    "https://discover.files.wordpress.com/2020/08/visual-priority-neuroscienews-public-390x390-1.jpg",
                    2
                )
            )
            add(
                Post(
                    "Red Herring Games: Navigating a Business During a Pandemic",
                    "<p>How having a WordPress site helped this business survive the challenges of COVID-19. </p>\\",
                    Author("Carolyn Wells", "onceuponatime66.wordpress.com"),
                    "2020-08-13T10:00:37-04:00",
                    "https://discover.wordpress.com/2020/08/13/red-herring-games/",
                    "https://discover.files.wordpress.com/2020/08/screen-shot-2020-08-10-at-4.46.42-pm.png",
                    2
                )
            )
        }
    }
}