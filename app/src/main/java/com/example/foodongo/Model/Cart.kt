package com.example.foodongo.Model

data class Cart (
   var  foodName : String ?= null,
   var  foodPrice : String ?= null,
   var  foodDescription : String ?= null,
   var  foodImage : String ?= null,
   var  foodIngridient : String ?= null,
    var foodQuantity : Int ?= null,

)