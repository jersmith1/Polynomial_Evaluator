package assignment2;

import java.math.BigInteger;

public class Polynomial implements DeepClone<Polynomial>
{
 private SLinkedList<Term> polynomial;
 public int size()
 { 
  return polynomial.size();
 }
 private Polynomial(SLinkedList<Term> p)
 {
  polynomial = p;
 }
 
 public Polynomial()
 {
  polynomial = new SLinkedList<Term>();
 }
 
 // Returns a deep copy of the object.
 public Polynomial deepClone()
 { 
  return new Polynomial(polynomial.deepClone());
 }
 
 /* 
  * TODO: Add new term to the polynomial. Also ensure the polynomial is
  * in decreasing order of exponent.
  */
 public void addTerm(Term t) //fix if exponents match
 {
  Term myT = t.deepClone();
  SLinkedList<Term> temp = new SLinkedList<Term>();
  boolean tUsed = false;
  BigInteger zero = new BigInteger("0");
  if((myT.getCoefficient()).equals(zero) == true){
    return;
  }
  if(polynomial.isEmpty() == true)
  {		
    temp.addLast(myT);			
  }
  else
  {
    for(Term curTerm: this.polynomial)
    {
      int curExp	= curTerm.getExponent();
      int myExp = myT.getExponent();
      BigInteger curCoef = curTerm.getCoefficient();
      BigInteger myCoef = myT.getCoefficient();
      if(tUsed == false)
      {
        if(myExp == curExp) {
          BigInteger tempCoef = myCoef.add(curCoef);
          if(tempCoef.equals(zero) == true) {
          }
          else
          {
            myT.setCoefficient(tempCoef);
            temp.addLast(myT);
          }
          tUsed = true;	
        } 
        else if(curExp < myExp){
          temp.addLast(myT);
          temp.addLast(curTerm);	
          tUsed = true;	
        }
        else
        {	
          temp.addLast(curTerm);
        }
      }
      else
      {
        temp.addLast(curTerm);
      }
    }
    if(tUsed == false)
      temp.addLast(myT);
  }
  this.polynomial = temp;
}
 
 public Term getTerm(int index)
 {
  return polynomial.get(index);
 }
 
 //TODO: Add two polynomial without modifying either
 public static Polynomial add(Polynomial p1, Polynomial p2)
 {
  if (p1.polynomial.size() == 0)
  {
    return p2.deepClone();
  }
  if (p2.polynomial.size() == 0)
  {
    return p1.deepClone();
  }
  Polynomial sum = new Polynomial();
  Polynomial deep1 = p1.deepClone();
  Polynomial deep2 = p2.deepClone();

  for (Term t1: deep1.polynomial)
  {
    sum.addTerm(t1);
  }
  for (Term t2: deep2.polynomial)
  {
    sum.addTerm(t2);
  }
  //add all of the terms from p2 into the sum polynomial
  /**** ADD CODE HERE ****/
  //returns the newly constructed polynomial of the sum of p1 and p2
  return sum;
 }
 
 //TODO: multiply this polynomial by a given term.
 public void multiplyTerm(Term t)
 {
   for (Term curTerm : this.polynomial)
   {
     BigInteger myCoef = curTerm.getCoefficient();
     BigInteger inputCoef = t.getCoefficient();
     int myExp = curTerm.getExponent();
     int inputExp = t.getExponent();

     if (myCoef.equals(new BigInteger("0")) || inputCoef.equals(new BigInteger("0")))
     {
         this.polynomial.clear();
         //curTerm.setCoefficient(new BigInteger("0"));
     }
     else
     {
         myCoef = myCoef.multiply(inputCoef);
         myExp += inputExp;

         curTerm.setCoefficient(myCoef);
         curTerm.setExponent(myExp);
     }


   } 
  /**** DONE :) ****/ 
 }
 
 //TODO: multiply two polynomials
 public static Polynomial multiply(Polynomial p1, Polynomial p2)
 {
  if (p1.size() == 0 || p2.size() == 0)
  {
    return new Polynomial();
  }
  Polynomial product = new Polynomial();
  for (Term t : p2.polynomial)
  {
    Polynomial deep1 = p1.deepClone();
    deep1.multiplyTerm(t);
    product = add(product, deep1);
  }
  return product; //need to check runtime since piazza hint said use diff data structure and i didnt idkidk
 }
 
 // TODO: evaluate this polynomial.
 // Hint:  The time complexity of eval() must be order O(m), 
 // where m is the largest degree of the polynomial. Notice 
 // that the function SLinkedList.get(index) method is O(m), 
 // so if your eval() method were to call the get(index) 
 // method m times then your eval method would be O(m^2).
 // Instead, use a Java enhanced for loop to iterate through 
 // the terms of an SLinkedList.

 public BigInteger eval(BigInteger x) //failing zero value exponent still
 {
   BigInteger temp = new BigInteger ("0");
   BigInteger result = new BigInteger("0");

   //if (x.equals(temp))
   //{
     //return new BigInteger("0");
   //}

   for (Term t: this.polynomial)
   {
     BigInteger c = t.getCoefficient();
     int e = t.getExponent();

     if (x.equals(temp) && e!=0)
     {
         temp = new BigInteger("0");
     }
     else if (x.equals(temp) && e==0)
     {
         temp = new BigInteger("1");
     }
     else if (e == 0 && !x.equals(temp))
     {
         temp = new BigInteger("1");
     }
     else
     {
         temp = x.pow(e);
     }
     temp = temp.multiply(c);
     result = result.add(temp);
   }
  /**** ADD CODE HERE ****/
  return result;
 }
 
 // Checks if this polynomial is a clone of the input polynomial
 public boolean isDeepClone(Polynomial p)
 { 
  if (p == null || polynomial == null || p.polynomial == null || this.size() != p.size())
   return false;

  int index = 0;
  for (Term term0 : polynomial)
  {
   Term term1 = p.getTerm(index);

   // making sure that p is a deep clone of this
   if (term0.getExponent() != term1.getExponent() ||
     term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || term1 == term0)  
    return false;

   index++;
  }
  return true;
 }
 
 // This method blindly adds a term to the end of LinkedList polynomial. 
 // Avoid using this method in your implementation as it is only used for testing.
 public void addTermLast(Term t)
 { 
  polynomial.addLast(t);
 }
 
 
 @Override
 public String toString()
 { 
  if (polynomial.size() == 0) return "0";
  return polynomial.toString();
 }
}
