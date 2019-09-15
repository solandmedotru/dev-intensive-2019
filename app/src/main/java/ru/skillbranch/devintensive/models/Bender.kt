package ru.skillbranch.devintensive.models

class Bender(var status:Status = Status.NORMAL, var question: Question = Question.NAME) {

   fun askQuestion()= when(question){
       Question.NAME -> Question.NAME.question
       Question.PROFESSION -> Question.PROFESSION.question
       Question.MATERIAL -> Question.MATERIAL.question
       Question.BDAY -> Question.BDAY.question
       Question.SERIAL -> Question.SERIAL.question
       Question.IDLE -> Question.IDLE.question
   }

    fun listenAnswer(answer:String) : Pair<String, Triple<Int, Int, Int>>{
        if (answer.isEmpty()) {
            return "${question.question}" to status.color
        }
        val(res, message) = question.valide(answer)
        if (res) {
            return if (question == Question.IDLE) {
                "Отлично - ты справился\n${question.question}" to status.color
            } else if (question.answers.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            } else {
                status = status.nextStatus()
                if (status != Status.NORMAL) {
                    "Это неправильный ответ\n${question.question}" to status.color
                } else {
                    question = Question.NAME
                    "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                }
            }
        } else {
            return "$message\n${question.question}" to status.color
        }
    }

    enum class Status(val color : Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus() : Status{
            return if(this.ordinal < values().lastIndex){
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }
    enum class Question(val question:String, val answers:List<String>){
        NAME("Как меня зовут?", listOf("бендер","bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun valide(answer: String): Pair<Boolean, String> =
                if (answer.isNotEmpty() && answer[0].isLetter() && answer[0].isUpperCase()) {
                    true to ""
                } else {
                    false to "Имя должно начинаться с заглавной буквы"
                }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальшик","bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun valide(answer: String): Pair<Boolean, String> =
                if (answer.isNotEmpty() && answer[0].isLetter() && answer[0].isLowerCase()) {
                    true to ""
                } else {
                    false to "Профессия должна начинаться со строчной буквы"
                }
        },
        MATERIAL("Из чего я сделан?", listOf("металл","дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun valide(answer: String): Pair<Boolean, String> =
                if ("[0-9]".toRegex().containsMatchIn(answer)) {
                //if (answer.contains("[0..9]".toRegex())) {
                   false to "Материал не должен содержать цифр"
                } else {
                    true to ""
                }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun valide(answer: String): Pair<Boolean, String> =
                if ("[^0-9]".toRegex().containsMatchIn(answer)) {
                    //if (answer.contains("^[0..9]".toRegex())) {
                    false to "Год моего рождения должен содержать только цифры"
                } else {
                    true to ""
                }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun valide(answer: String): Pair<Boolean, String> =
                if (answer.length != 7 || "[^0-9]".toRegex().containsMatchIn(answer)) {
                //if (answer.length != 7 && answer.contains("^[0..9]".toRegex())) {
                    false to "Серийный номер содержит только цифры, и их 7"
                } else {
                    true to ""
                }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun valide(answer: String): Pair<Boolean, String> = true to ""
        };

        abstract fun nextQuestion() : Question
        abstract fun valide(answer:String) : Pair<Boolean, String>
    }
}