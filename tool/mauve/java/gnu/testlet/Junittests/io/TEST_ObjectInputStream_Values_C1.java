/**************************************************************************
* Copyright  (c) 2001 by Acunia N.V. All rights reserved.                 *
*                                                                         *
* This software is copyrighted by and is the sole property of Acunia N.V. *
* and its licensors, if any. All rights, title, ownership, or other       *
* interests in the software remain the property of Acunia N.V. and its    *
* licensors, if any.                                                      *
*                                                                         *
* This software may only be used in accordance with the corresponding     *
* license agreement. Any unauthorized use, duplication, transmission,     *
*  distribution or disclosure of this software is expressly forbidden.    *
*                                                                         *
* This Copyright notice may not be removed or modified without prior      *
* written consent of Acunia N.V.                                          *
*                                                                         *
* Acunia N.V. reserves the right to modify this software without notice.  *
*                                                                         *
*   Acunia N.V.                                                           *
*   Vanden Tymplestraat 35      info@acunia.com                           *
*   3000 Leuven                 http://www.acunia.com                     *
*   Belgium - EUROPE                                                      *
**************************************************************************/


package gnu.testlet.Junittests.io;

import java.io.*;

import gnu.testlet.*;
import java.util.Arrays;


public class TEST_ObjectInputStream_Values_C1 extends Mv_Assert {


  private SAMPLE_ObjectStream_C original, check;

  private Object object;

  private ObjectInputStream ois;
  private ByteArrayInputStream bais;

  private byte[] data;

  private final static double DELTA=0.000001;


  public TEST_ObjectInputStream_Values_C1() {
    super();

    data = new byte[SAMPLE_SerialObject.dataInt.length];
    for (int i=0;i<SAMPLE_SerialObject.dataInt.length;i++) {
      data[i]=(byte)SAMPLE_SerialObject.dataInt[i];
    }

    original=(SAMPLE_ObjectStream_C)SAMPLE_SerialObject.getRootObject().getRight();


  }

  public void setUp() {
    openStream(data);

    try {
      object = ois.readObject();
    }catch(Exception e) {
      fail(e.toString());
    }
    closeStream();


    //b1=(SAMPLE_ObjectStream_B)object;
    check=(SAMPLE_ObjectStream_C)(((SAMPLE_ObjectStream_B)object).getRight());
  }



  public void tearDown() {
  }


  public void closeStream() {
    try {
      bais.close();
    }catch(IOException ioe) {
      fail("IO Exception while closing input stream");
    }

  }

  private void openStream(byte [] data) {
    try {
      bais=new ByteArrayInputStream(data);
      ois=new ObjectInputStream(bais);
    }catch(IOException ioe) {
      fail("openStream> "+ioe.toString());
      return;
    }
  }




  protected void runTest() 
    {
    assertEqual( check.getTestByte(), original.getTestByte());
    assertEqual (check.getTestInt(), original.getTestInt());
    assertApproximatelyEqual( check.getTestFloat(), original.getTestFloat(), DELTA);
    assertEqual( check.getTestChar(), original.getTestChar());
    assertEqual(check.getTestShort(), original.getTestShort());
    assertEqual(check.getTestLong(), original.getTestLong());
    assertApproximatelyEqual(check.getTestDouble(), original.getTestDouble(),DELTA);
    assertTrue("boolean values are not equal",check.getTestBoolean() == original.getTestBoolean());
    assertTrue(check.getTransientInt()==0);
    assertTrue(check.getVolatileInt()==original.getVolatileInt());
    assertTrue(check.getD().toString().equals(original.getD().toString()));
    assertNull(check.getTransientD());
    }


}
