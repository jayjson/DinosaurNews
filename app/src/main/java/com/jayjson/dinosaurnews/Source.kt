package com.jayjson.dinosaurnews

data class Source(val id: String? = null,
                  val name: String,
                  val description: String? = null,
                  val url: String? = null,
                  val category: String? = null,
                  val language: String? = null,
                  val country: String? = null)

enum class SourceCategory {
    BUSINESS,
    ENTERTAINMENT,
    GENERAL,
    HEALTH,
    SCIENCE,
    SPORTS,
    TECHNOLOGY
}

enum class Language {
    AR, DE, EN, ES, FR, HE, IT, NL, NO, PT, RU, SV, UD, ZH
}

enum class Country {
    AE, AR, AT, AU, BE, BG, BR, CA, CH, CN, CO, CU, CZ, DE, EG, FR, GB, GR, HK, HU, ID, IE, IL, IN, IT, JP, KR, LT, LV, MA, MX, MY, NG, NL, NO, NZ, PH, PL, PT, RO, RS, RU, SA, SE, SG, SI, SK, TH, TR, TW, UA, US, VE, ZA
}