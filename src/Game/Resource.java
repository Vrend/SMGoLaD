package Game;

//Resource parent class
public abstract class Resource
{
    //instance fields
    private String name;
    private int amount;

    //constructor
    public Resource(String n, int a)
    {
        name = n;
        amount = a;
    }

    //returns name
    public String getName()
    {
        return name;
    }

    //returns amount
    public int getAmount()
    {
        return amount;
    }

    //sets amount
    public void setAmount(int num)
    {
        amount = num;
    }
}
