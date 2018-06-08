object Learning {
  def main(args: Array[String]): Unit = {
    val hello: String = "Hola"
    println(hello)

    // var can be changed, val cannot
    var helloThere: String = hello
    helloThere = hello + " There!"
    println(helloThere)

    val immutableHelloThere = hello + " There!"
    println(immutableHelloThere)

    // Tuples
    val captainStuff = ("Picard", "Enterprise-D", "NCC-1701-D")

    // Extracting data from a tuple (1-based index)
    println(captainStuff._1)
    println(captainStuff._2)
    println(captainStuff._3)

    // key/value pair
    val picardsShip = "Picard" -> "Enterpise-D"
    println(picardsShip._1) // picard
    println(picardsShip._2) // enterprise-d

    // Lists (singly-linked list) (0-based index)
    val shipList = List("Enterprise", "Defiant", "Voyager")
    println(shipList(1)) // gives us Defiant

    for (ship <- shipList) { println(ship) }

    val backwardsShip = shipList.map((ship: String) => { ship.reverse })

    // reduce() can be used to combine together all the times in a collection
    // using some functions
    val numberList = List(1, 2, 3, 4, 5)
    val sum = numberList.reduce( (x: Int, y: Int) => x + y)

    // filter() allows us to remove stuff we don't want
    val iHateFives = numberList.filter(x => x != 5)
    val iHateThrees = numberList.filter(_ != 3)

    // concatenating lists
    val moreNumbers = List(6, 7, 8)
    val lotsOfNumbers = numberList ++ moreNumbers

    // more list functions
    val reversed = numberList.reverse
    val sorted = reversed.sorted
    val lotsOfDuplicates = numberList ++ numberList
    val distinctValues = lotsOfDuplicates.distinct
    val maxValue = numberList.max
    val total = numberList.sum
    val hasThree = numberList.contains(3)

    // maps
    val shipMap = Map(
      "Kirk" -> "Enterprise",
      "Picard" -> "Enterprise-D"
    )

    println(shipMap("Kirk")) // returns Enterprise

    // Try-Catch Block
    val archersShip = util.Try(shipMap("Archer")) getOrElse("Unknown")
    println(archersShip)
  }
}
