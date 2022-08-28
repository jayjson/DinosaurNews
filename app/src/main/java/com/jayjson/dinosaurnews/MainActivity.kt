package com.jayjson.dinosaurnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.children
import com.jayjson.dinosaurnews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val sources = mapOf(
        "Reuters" to Source("reuters", "Reuters"),
        "The Guardian" to Source(null, "The Guardian"),
        "Business Insider" to Source("business-insider", "Business Insider")
    )

    private val defaultSource = Source(null, "")

    private val articles = arrayListOf(
        Article(
            sources["Reuters"] ?: defaultSource,
            null,
            "BlackRock launches spot bitcoin private trust for U.S. clients - Reuters",
            "BlackRock Inc <a href=\"https://www.reuters.com/companies/BLK.N\" target=\"_blank\">(BLK.N)</a>, the world's biggest asset manager, has launched a spot bitcoin private trust for institutional clients in the United States, according to a blog post on its website.",
            "https://www.reuters.com/technology/blackrock-launches-spot-bitcoin-private-trust-us-clients-2022-08-11/",
            "https://www.reuters.com/resizer/dm3gxxy55l135TSvy6WVVBxoBMo=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/RW5HROCP6NNKXKM3TSRKNQRYSQ.jpg",
            "2022-08-11T13:49:00Z",
            "Aug 11 (Reuters) - BlackRock Inc (BLK.N), the world's biggest asset manager, has launched a spot bitcoin private trust for institutional clients in the United States, according to a blog post on its … [+1171 chars]"
        ),
        Article(
            sources["Reuters"] ?: defaultSource,
            null,
            "Honduras launches 'Bitcoin Valley' in the tourist town of Santa Lucia - Reuters.com",
            "People can pay for a slushie with crypto in the streets of \"Bitcoin Valley,\" a project in the Honduran tourist enclave of Santa Lucia through which the country has entered the digital currency trend.",
            "https://www.reuters.com/world/americas/honduras-launches-bitcoin-valley-tourist-town-santa-lucia-2022-07-29/",
            "https://www.reuters.com/resizer/jPZytgVrBXWFzrPY9IVzSoBkS6w=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/4ZHVPFZ4KJJITKXCQMIMCUFRDM.jpg",
            "2022-07-29T19:46:00Z",
            "July 29 (Reuters) - People can pay for a slushie with crypto in the streets of \"Bitcoin Valley,\" a project in the Honduran tourist enclave of Santa Lucia through which the country has entered the dig… [+2277 chars]"
        ),
        Article(
            sources["The Guardian"] ?: defaultSource,
            "Alex Hern",
            "I’m no longer making predictions about cryptocurrency. Here’s why",
            "If you’d bought or sold bitcoin every time I wrote about it over the last decade, how much would you have made? Let’s do the maths<ul><li>Don’t get TechScape delivered to your inbox? Sign up here</li></ul>I’ve been writing about cryptocurrency for my entire c…",
            "https://amp.theguardian.com/technology/2022/aug/03/techscape-bitcoin-cryptocurrency-predictions",
            "https://i.guim.co.uk/img/media/47c0e67fc97c6e8f15012744ac879ce0918cad9e/46_319_3487_2092/master/3487.jpg?width=1200&height=630&quality=85&auto=format&fit=crop&overlay-align=bottom%2Cleft&overlay-width=100p&overlay-base64=L2ltZy9zdGF0aWMvb3ZlcmxheXMvdGctZGVmYXVsdC5wbmc&enable=upscale&s=59218b55d3e48d92b1ab13f378cbf129",
            "2022-08-03T10:45:25Z",
            "Ive been writing about cryptocurrency for my entire career. In that time, one point Ive always stuck to is simple: dont listen to me for investment advice. Today, I want to quantify why.\r\nBitcoin was… [+7658 chars]"
        ),
        Article(
            sources["Business Insider"] ?: defaultSource,
            "insider@insider.com (Carla Mozée)",
            "Michael Saylor to step down as MicroStrategy CEO as the software maker records $917 million charge on bitcoin investment",
            "Saylor will become executive chairman at MicroStrategy and will focus more on the company's bitcoin acquisition strategy.",
            "https://markets.businessinsider.com/news/currencies/microstrategy-bitcoin-michael-saylor-ceo-917-million-impairment-charge-crypto-022-8",
            "https://i.insider.com/60ca262123393a00188e3872?width=1200&format=jpeg",
            "2022-08-03T13:37:24Z",
            "MicroStrategy's founder Michael Saylor will step down as CEO, with the move coming after the enterprise software maker took a quarterly impairment charge of more than $900 million related to the drop… [+1552 chars]"
        ),
        Article(
            sources["Business Insider"] ?: defaultSource,
            "insider@insider.com (Davíd Lavie)",
            "Bitcoin halving is how the supply of the world's largest cryptocurrency is controlled",
            "Bitcoin halving is when the rate of new bitcoins entering circulation is cut in half, which occurs approximately every four years.",
            "https://www.businessinsider.com/personal-finance/bitcoin-halving",
            "https://i.insider.com/619d45c2d2fd62001820b8d0?width=1200&format=jpeg",
            "2022-08-16T16:59:51Z",
            "Despite its somewhat nebulous-seeming nature, Bitcoin is a finite resource. There will only ever be 21 million Bitcoin. So far, 19 million have already been mined, meaning there's just two million Bi… [+6317 chars]"
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        populateArticles()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun populateArticles() {
        val mainGroup = binding.mainGroup

        var index = 0;
        for (childView in mainGroup.children) {
            if (childView is TextView) {
                val article = articles[index]
                childView.text = prepareDisplayText(article.title, article.author, article.source.name)
                index++
            }
        }
    }

    private fun prepareDisplayText(title: String, author: String? = null, sourceName: String): String {
        var textToDisplay = "$title"
        if (author != null) {
            textToDisplay += " by $author"
        }
        textToDisplay += " ($sourceName)"
        return textToDisplay
    }
}