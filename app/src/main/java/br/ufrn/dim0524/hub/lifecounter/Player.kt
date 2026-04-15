package br.ufrn.dim0524.hub.lifecounter

enum class Type{
    BLACK,
    GREEN,
    WHITE,
    BLUE,
    RED
}

class Player(private var hp: Int, private var type: Type) {

    fun getHp(): Int {
        return hp
    }

    fun setHp(hp: Int) {
        this.hp = hp
    }

    fun getType(): Type {
        return type
    }

    fun setType(type: Type) {
        this.type = type
    }

    fun receiveAttack() {
        hp--
    }

    fun receiveLife() {
        hp++
    }
}