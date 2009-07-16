/**************************************************************************
* Copyright (c) 2009 by Chris Gray, /k/ Embedded Java Solutions.          *
* All rights reserved.                                                    *
*                                                                         *
* Redistribution and use in source and binary forms, with or without      *
* modification, are permitted provided that the following conditions      *
* are met:                                                                *
* 1. Redistributions of source code must retain the above copyright       *
*    notice, this list of conditions and the following disclaimer.        *
* 2. Redistributions in binary form must reproduce the above copyright    *
*    notice, this list of conditions and the following disclaimer in the  *
*    documentation and/or other materials provided with the distribution. *
* 3. Neither the name of /k/ Embedded Java Solutions nor the names of     *
*    other contributors may be used to endorse or promote products        *
*    derived from this software without specific prior written            *
*    permission.                                                          *
*                                                                         *
* THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESS OR IMPLIED          *
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF    *
* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.    *
* IN NO EVENT SHALL /K/ EMBEDDED JAVA SOLUTIONS OR OTHER CONTRIBUTORS     *
* BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,     *
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT    *
* OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR      *
* BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,   *
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE    *
* OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,       *
* EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                      *
**************************************************************************/

package java.net;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

class SocketUsers {

  private static HashMap psi2t = new HashMap();

  private static int putcount;

  static synchronized Thread put(PlainSocketImpl psi, Thread t) {
    WeakReference wr = (WeakReference)psi2t.put(psi, new WeakReference(t));
    if (++putcount >= 100) {
      putcount = 0;
      // Clean up stale references
      Iterator iter = psi2t.entrySet().iterator();
      while (iter.hasNext()) {
        WeakReference wr0 = (WeakReference)((Map.Entry)iter.next()).getValue();
        if (wr0.get() == null) {
          iter.remove();
        }
      }
    }
    return wr == null ? null : (Thread)wr.get();
  }

  static synchronized Thread remove(PlainSocketImpl psi) {
    WeakReference wr = (WeakReference)psi2t.remove(psi);
    return wr == null ? null : (Thread)wr.get();
  }

  static synchronized void clear() {
    psi2t.clear();
  }

  static synchronized Thread get(PlainSocketImpl psi) {
    WeakReference wr = (WeakReference)psi2t.get(psi);
    if (wr == null) {
      return null;
    }
    Thread t = (Thread)wr.get();
    if (t == null) {
      psi2t.remove(psi);
    }

    return t;
  }
}
