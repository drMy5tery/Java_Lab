
class Credit_card{
    StringBuilder card_number;

    // Parameterized Constructor
    Credit_card(StringBuilder card_number) {
        this.card_number = card_number;
        System.out.println("Original Card Number: " + this.card_number);
    }
    
    boolean isvalid(StringBuilder card_number) {
        int str_len = card_number.length();
        if(!(8 <= str_len && str_len <= 9 )){
            return false;
        }

        boolean result = true;
        char last_char,temp_char;
        int ascii,value,sum = 0;
        str_len -= 1; //Reducing the length
        last_char = card_number.charAt(str_len); //  Storing last character
            // Switch case
        switch ('a') {
        // Case a Reducing length and removing last element
        case 'a': 
            
            card_number.deleteCharAt(str_len); // removing the last char
            System.out.println("Card Number After Removing Last Number: " + card_number);

        // Case b Reversing the String
        case 'b':
            card_number.reverse(); //reversing the string
            System.out.println("Reversed Card Number: " + card_number);
            
        // Case c Doubling odd places in the Card
        case 'c':
            for(int i = 0;i < str_len;i += 2){
                temp_char = card_number.charAt(i);
                ascii = temp_char;
                value = Character.getNumericValue(temp_char);
                if (ascii >= 53){
                    value -= 9;    
                }
                ascii += value;
                card_number.setCharAt(i, (char) ascii);
            }
            System.out.println("Card Number After doubling Odd Places: " + card_number);
            

        // Case d Getting Total Sum
        case 'd':
            for(int i = 0;i < str_len; i++){
                temp_char = card_number.charAt(i);
                value = Character.getNumericValue(temp_char);
                sum += value;
            }
            System.out.println("Total Sum after doubling odd places: " + sum);
            

        // Case e Subtracting The last digit from 10
        case 'e':
            sum %= 10;
            sum = 10 - sum;
            System.out.println("Value after subtracting last digit from 10: " + sum);

        // Case 6 Comparing Value with the initial last character
        default:
            value = Character.getNumericValue(last_char);
            if( value != sum){
                result = false;
            }
            break;
        }
        return result;
    }
}


public class Part_1{
    public static void main(String[] args) {
        //Scanner objScanner = new Scanner(System.in);

        //System.out.println("Enter the Credit Card Nunmber: ");
    
        // String input
        //int int_card_number = objScanner.nextInt();
        int int_card_number = 123467575;
        StringBuilder sb_card_number = new StringBuilder(String.valueOf(int_card_number));
        Credit_card creditCard = new Credit_card(sb_card_number);
        System.out.println("");
        if (creditCard.isvalid(sb_card_number)){
            System.out.println("Given card is Valid");
        }

        else{
            System.out.println("Given card is InValid");
        }

        
    }
}