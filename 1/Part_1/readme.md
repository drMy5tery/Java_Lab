The Lab program here tries to validate a credit card of length 8 or 9 <br />
For the Algorithm to compute and double the digits at the Odd places I've used the Ascii values <br />
Observe the pattern in the below displayed  Observed Pattern<br />

Observed Pattern:<br />
0's ascii value is 48, 0 x 2 = 0's ascii value is 48, 48 + 0 <br />
1's ascii value is 49, 1 x 2 = 2's ascii value is 50, 49 + 1 <br />
2's ascii value is 50, 2 x 2 = 4's ascii value is 52, 50 + 2 <br />
3's ascii value is 51, 3 x 2 = 6's ascii value is 54, 51 + 3 <br />
4's ascii value is 52, 4 x 2 = 8's ascii value is 56, 52 + 4 <br />
5's ascii value is 53, 5 x 2 = 10, 10 = 1 + 0 = 1's ascii value is 49, 53 - 4, where 4 = 9 - 5 <br /> 
6's ascii value is 54, 6 x 2 = 12, 12 = 1 + 2 = 3's ascii value is 51, 54 - 3, where 3 = 9 - 6 <br /> 
7's ascii value is 55, 7 x 2 = 14, 14 = 1 + 4 = 5's ascii value is 53, 55 - 2, where 2 = 9 - 7 <br />
8's ascii value is 56, 8 x 2 = 16, 16 = 1 + 6 = 7's ascii value is 55, 56 - 1, where 1 = 9 - 8 <br />
9's ascii value is 57, 9 x 2 = 18, 18 = 1 + 8 = 9's ascii value is 57, 57 - 0, where 0 = 9 - 9 <br />

If you  observe the above pattern the ascii value just adds with it's own respective integer value for until 4 for doubling of the integer but after 5 based on our calculation to add the obtained product sum , we get the ascii value to be decreasing from -4. the pattern array looks like this [0,1,2,3,4,-4,-3,-2,-1,0]. <br />

Based on this the computation has been done.
