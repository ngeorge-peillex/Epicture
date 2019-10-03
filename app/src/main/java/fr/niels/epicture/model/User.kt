package fr.niels.epicture.model

import java.util.Observable

class User: Observable() {
    var id: String = ""
        set(value) {
            field = value
            setChangedAndNotify("id")
        }

    var username: String = ""
        set(value) {
            field = value
            setChangedAndNotify("username")
        }

    private fun setChangedAndNotify(field: Any)
    {
        setChanged()
        notifyObservers(field)
    }
}