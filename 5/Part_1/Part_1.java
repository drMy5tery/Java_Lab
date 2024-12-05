interface BankInterface{
    double getBalance();
    float getInteresRate();
}

class BankA implements BankInterface{

    double depAmount;

    BankA(double depAmount){
        this.depAmount = depAmount;
    }

    @Override
    public double getBalance(){
        return depAmount;
    }

    @Override
    public float getInteresRate(){
        return 0.07f;
    }
}

class BankB implements BankInterface{
    double depAmount;

    BankB(double depAmount){
        this.depAmount = depAmount;
    }

    @Override
    public double getBalance(){
        return depAmount;
    }

    @Override
    public float getInteresRate(){
        return 7.4f;
    }

}
class BankC implements BankInterface{
    double depAmount;

    BankC(double depAmount){
        this.depAmount = depAmount;
    }

    @Override
    public double getBalance(){
        return depAmount;
    }

    @Override
    public float getInteresRate(){
        return 7.9f;
    }
}

public class Part_1{
    public static void main(String[] args) {
        BankA a = new BankA(10000);
        BankB b = new BankB(150000);
        BankC c = new BankC(200000);
        displayDetails("Bank A",a.getBalance(),a.getInteresRate());
        displayDetails("Bank B",b.getBalance(),b.getInteresRate());
        displayDetails("Bank C",c.getBalance(),c.getInteresRate());
    }

    private static void displayDetails(String bank_name,double amount, float iRate){
        System.out.println("");
        System.out.println("________________________");
        System.out.println("Bank Name: " + bank_name);
        System.out.println("Deposit Amount: " + amount);
        System.out.println("Interest Rate: " + iRate);
    } 
}