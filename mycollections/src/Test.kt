fun <T> Iterable<T>.newline() = joinToString(separator = "\n")

/*--------------------------- toSet, toList ---------------------------*/

fun Shop.getSetOfCustomers(): Set<Customer> = customers.toSet()

/*--------------------------- filter; map ---------------------------*/

// Return the set of cities the customers are from
fun Shop.getCitiesCustomersAreFrom(): Set<City> = customers.map { it.city }.toSet()
// or
// groupedByCities.map { it.key }.toSet()

// Return a list of the customers who live in the given city
fun Shop.getCustomersFrom(city: City): List<Customer> = customers.filter { it.city == city }
// or
// shop.customers.filter { it.city == city }

/*--------------------------- All, Any, Count, Find ---------------------------*/

// Return true if all customers are from the given city
fun Shop.checkAllCustomersAreFrom(city: City): Boolean = customers.all { it.city == city }

//​
//// Return true if there is at least one customer from the given city
fun Shop.hasCustomerFrom(city: City): Boolean = customers.any { it.city == city }

//​
//// Return the number of customers from the given city
fun Shop.countCustomersFrom(city: City): Int = customers.count { it.city == city }

//​
//// Return a customer who lives in the given city, or null if there is none
fun Shop.findAnyCustomerFrom(city: City): Customer? = customers.find { it.city == city }

/*--------------------------- FlatMap ---------------------------*/

// Return all products this customer has ordered
val Customer.orderedProducts: Set<Product>
    get() {
        return this.orders.flatMap { it.products }.toSet()
    }

// Return all products that were ordered by at least one customer
val Shop.allOrderedProducts: Set<Product>
    get() {
        return customers.flatMap { it.orderedProducts }.toSet()
        // or
        // return this.customers.flatMap { it.orders }.flatMap { it.products }.toSet()
    }

/*--------------------------- max, min, maxBy, minBy ---------------------------*/

// Return a customer whose order count is the highest among all customers
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxBy { it.orders.size }

// Return the most expensive product which has been ordered
fun Customer.getMostExpensiveOrderedProduct(): Product? = orders.flatMap { it.products }.maxBy { it.price }
// or
// orderedProducts.maxBy { it.price }

/*--------------------------- sorted, sortedBy ---------------------------*/

// Return a list of customers, sorted by the ascending number of orders they made
fun Shop.getCustomersSortedByNumberOfOrders(): List<Customer> = customers.sortedBy { it.orders.size }

/*--------------------------- sum, sumBy ---------------------------*/

// Return the sum of prices of all products that a customer has ordered.
// Note: the customer may order the same product for several times.
fun Customer.getTotalOrderPrice(): Double = orders.flatMap { it.products }.sumByDouble { it.price }

/*--------------------------- groupBy ---------------------------*/

// Return a map of the customers living in each city
fun Shop.groupCustomersByCity(): Map<City, List<Customer>> = customers.groupBy { it.city }

fun main() {
    val c = "-".repeat(78)
    val bangkok = City("Bangkok")
    val mrLucas = customers.get(lucas)

    println(c)
    when (8) {
        1 -> {
            println(shop.getSetOfCustomers().newline())
        }
        2 -> {
            println(shop.getCitiesCustomersAreFrom())
            println(c)
            println(shop.getCustomersFrom(Tokyo).newline())
        }
        3 -> {
            println(shop.checkAllCustomersAreFrom(Tokyo))
            println(shop.hasCustomerFrom(Tokyo))
            println(shop.hasCustomerFrom(bangkok))
            println(shop.countCustomersFrom(Tokyo))
            println(shop.countCustomersFrom(Vancouver))
            println(shop.findAnyCustomerFrom(Tokyo))
            println(shop.findAnyCustomerFrom(bangkok))
        }
        4 -> {
            println(mrLucas?.orderedProducts)
            println(shop.allOrderedProducts.newline())
        }
        5 -> {
            println(shop.getCustomerWithMaximumNumberOfOrders())
            println(mrLucas?.getMostExpensiveOrderedProduct())
        }
        6 -> {
            println(shop.getCustomersSortedByNumberOfOrders().newline())
        }
        7 -> {
            println(mrLucas?.getTotalOrderPrice())
        }
        8 -> {
            println(shop.groupCustomersByCity().toList().newline())
        }
    }
    println(c)
}
