/**************************************************************************
* Parts copyright (c) 2001 by Punch Telematix. All rights reserved.       *
* Parts copyright (c) 2009 by Chris Gray, /k/ Embedded Java Solutions.    *
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
* 3. Neither the name of Punch Telematix or of /k/ Embedded Java Solutions*
*    nor the names of other contributors may be used to endorse or promote*
*    products derived from this software without specific prior written   *
*    permission.                                                          *
*                                                                         *
* THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESS OR IMPLIED          *
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF    *
* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.    *
* IN NO EVENT SHALL PUNCH TELEMATIX, /K/ EMBEDDED JAVA SOLUTIONS OR OTHER *
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,   *
* EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,     *
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR      *
* PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF  *
* LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING    *
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS      *
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.            *
**************************************************************************/

package java.io;

import wonka.vm.SecurityConfiguration;
import java.security.AccessController;

/*
** Package-visible class used to get system properties with full privileges.
*/

class GetSystemProperty implements java.security.PrivilegedAction {

  static final String LINE_SEPARATOR;
  static final String FILE_SEPARATOR;
  static final String PATH_SEPARATOR;
  static final String FILE_ENCODING;
  static final String TMPDIR;

  private String key;
  private String dflt;
  private String value;

  /**
   ** Our static initialiser fetches the five system properties which
   ** everybody in this package seems to need ...
   */
  static {
    GetSystemProperty gsp;

    if (SecurityConfiguration.ENABLE_SECURITY_CHECKS) {
      gsp = new GetSystemProperty("line.separator","\n");
      AccessController.doPrivileged(gsp);
      LINE_SEPARATOR = gsp.get();

      gsp = new GetSystemProperty("file.separator","/");
      AccessController.doPrivileged(gsp);
      FILE_SEPARATOR = gsp.get();

      gsp = new GetSystemProperty("path.separator",":");
      AccessController.doPrivileged(gsp);
      PATH_SEPARATOR = gsp.get();

      gsp = new GetSystemProperty("file.encoding","8859_1");
      AccessController.doPrivileged(gsp);
      FILE_ENCODING = gsp.get();

      gsp = new GetSystemProperty("java.io.tmpdir","/tmp");
      AccessController.doPrivileged(gsp);
      TMPDIR = gsp.get();

      gsp = null;
    }
    else {
      LINE_SEPARATOR = System.getProperty("line.separator", "\n");
      FILE_SEPARATOR = System.getProperty("file.separator", "/");
      PATH_SEPARATOR = System.getProperty("path.separator", ":");
      FILE_ENCODING  = System.getProperty("file.encoding", "8859_1");
      TMPDIR = System.getProperty("java.io.tmpdir", "/tmp");
    }
  }

  /**
   ** Constructor which takes a key and a default value.
   */
  public GetSystemProperty(String key, String dflt) {
    this.key = key;
    this.dflt = dflt;
  }

  /**
   ** Constructor which just takes a key.
   */
  public GetSystemProperty(String key) {
    this.key = key;
  }

  /**
   ** This gets called by doPrivileged().
   */
  public Object run() {
    if (dflt == null) {
      value = System.getProperty(key);
    }
    else {
      value = System.getProperty(key, dflt);
    }

    return null;
  }

  /**
   ** Return the value to the caller.
   */
  public String get() {
    return value;
  }

}
