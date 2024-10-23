## The `maxProfit` Method:

The goal of this method is to calculate the maximum profit that can be achieved by performing at most two buy-sell transactions.

- **`firstBuy`**:  
  Stores the maximum profit (or least cost) after the first "buy" operation. Initially, it is set to `Integer.MIN_VALUE` to ensure that any price encountered on the first day will be treated as a valid "buy."

- **`firstSell`**:  
  Stores the maximum profit after the first "sell" operation. Initially, it is `0`, meaning no profit has been made yet.

- **`secondBuy`**:  
  Stores the maximum profit (or least cost) after the second "buy." It is initialized similarly to `firstBuy` to track the most favorable situation for buying again.

- **`secondSell`**:  
  Stores the maximum profit after the second "sell," initialized to `0` to track the profit after the second transaction.
