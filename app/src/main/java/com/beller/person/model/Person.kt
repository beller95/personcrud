package com.beller.person.model

import java.util.*

class Person {

    class Gender {
        companion object {
            const val MALE = "male"
            const val FEMALE = "female"
            const val OTHER = "other"
            const val UNKNOWN = "unknown"
        }
    }

    /*Person (DomainResource)
    identifier : Identifier [0..*]
    name : HumanName [0..*]
    telecom : ContactPoint [0..*]
    gender : code [0..1] « AdministrativeGender! »
    birthDate : date [0..1]
    address : Address [0..*]
    photo : Attachment [0..1]
    managingOrganization : Reference [0..1] « Organization »
    active : boolean [0..1*/



    public var identifier: String? = null
    public var name: HumanName? = null
    public var telecom: ContactPoint? = null
    public var gender: String = Gender.UNKNOWN
    public var birthDate: Date? = null
    public var address: Address? = null
    public var photo: Base64? = null
    public var managingOrganization: Int? = null
    public var active: Boolean? = null
    public var link: Int? = null

    constructor(
        identifier: String?,
        name: HumanName?,
        telecom: ContactPoint?,
        gender: String,
        birthDate: Date?,
        address: Address?,
        photo: Base64?,
        managingOrganization: Int?,
        active: Boolean?,
        link: Int?
    ) {
        this.identifier = identifier
        this.name = name
        this.telecom = telecom
        this.gender = gender
        this.birthDate = birthDate
        this.address = address
        this.photo = photo
        this.managingOrganization = managingOrganization
        this.active = active
        this.link = link
    }

    constructor(){}


}