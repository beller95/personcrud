package com.beller.person.model

class Address {

    public var line: String? = null
    private var city: String? = null
    private var postalCode: Int? = null
    private var country: String? = null
    public var display: String? = null

    constructor(display: String?) {
        this.display = display
    }
    constructor(){}
}
