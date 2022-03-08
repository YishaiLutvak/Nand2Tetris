package targil0

//input to text: src\targil0

class Ex0 {

    static File out = null
    static float sumOfBuying = 0
    static float sumOfSelling = 0

    static main(args) {
        if(args.length == 0){
            println(/Missing argument!!!/)
            return
        }
        File dir = new File(args[0])
        if (!dir.isDirectory()) {
            println(/Dir does not exists!!!/)
            return
        }
        out = new File(dir, $/${dir.name}.asm/$) << ''
        handleVmFiles(dir)
        out << $/TOTAL BUY: ${sumOfBuying.round(1)}\n/$
        out << $/TOTAL SELL: ${sumOfSelling.round(1)}\n/$
    }

    static void handleVmFiles(dir) {
        dir.eachFileMatch(~/.*\.vm/) {
            out << it.name.split(/\./)[0] << '\n'
            it.eachLine() {
                def splitLine = it.split(/ /)
                def startsWith = splitLine[0]
                def productName = splitLine[1]
                def amount = splitLine[2] as int
                def price = splitLine[3] as float
                startsWith == 'buy' ? handleBuy(productName, amount, price) : handleSell(productName, amount, price)
            }
        }
    }

    static void handleBuy(productName, amount, price) {
        def cost = computeCost(amount, price)
        sumOfBuying += cost
        out << "### BUY ${productName} ###\n"
        out << "${cost}\n"
    }

    static void handleSell(productName, amount, price) {
        def cost = computeCost(amount, price)
        sumOfSelling += cost
        out << "\$\$\$ SELL ${productName} \$\$\$\n"
        out << "${cost}\n"
    }

    static float computeCost(int amount,float price) {
        float multi = amount * price
        multi.round(1)
    }
}
