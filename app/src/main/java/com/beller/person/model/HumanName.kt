package com.beller.person.model

class HumanName {

    public var family: String? = null
    public var given: String? = null

    constructor(family: String?, given: String?) {
        this.family = family
        this.given = given
    }

    constructor(){}
}
