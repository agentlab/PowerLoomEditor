//  -*- Mode: Java -*-
//
// nameToLookup.java

/*
+---------------------------- BEGIN LICENSE BLOCK ---------------------------+
|                                                                            |
| Version: MPL 1.1/GPL 2.0/LGPL 2.1                                          |
|                                                                            |
| The contents of this file are subject to the Mozilla Public License        |
| Version 1.1 (the "License"); you may not use this file except in           |
| compliance with the License. You may obtain a copy of the License at       |
| http://www.mozilla.org/MPL/                                                |
|                                                                            |
| Software distributed under the License is distributed on an "AS IS" basis, |
| WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License   |
| for the specific language governing rights and limitations under the       |
| License.                                                                   |
|                                                                            |
| The Original Code is the STELLA Programming Language.                      |
|                                                                            |
| The Initial Developer of the Original Code is                              |
| UNIVERSITY OF SOUTHERN CALIFORNIA, INFORMATION SCIENCES INSTITUTE          |
| 4676 Admiralty Way, Marina Del Rey, California 90292, U.S.A.               |
|                                                                            |
| Portions created by the Initial Developer are Copyright (C) 2003-2012      |
| the Initial Developer. All Rights Reserved.                                |
|                                                                            |
| Contributor(s):                                                            |
|                                                                            |
| Alternatively, the contents of this file may be used under the terms of    |
| either the GNU General Public License Version 2 or later (the "GPL"), or   |
| the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),   |
| in which case the provisions of the GPL or the LGPL are applicable instead |
| of those above. If you wish to allow use of your version of this file only |
| under the terms of either the GPL or the LGPL, and not to allow others to  |
| use your version of this file under the terms of the MPL, indicate your    |
| decision by deleting the provisions above and replace them with the notice |
| and other provisions required by the GPL or the LGPL. If you do not delete |
| the provisions above, a recipient may use your version of this file under  |
| the terms of any one of the MPL, the GPL or the LGPL.                      |
|                                                                            |
+---------------------------- END LICENSE BLOCK -----------------------------+
*/

package edu.isi.webtools.examples.addressexample;

import edu.isi.stella.javalib.Native;
import edu.isi.stella.javalib.StellaSpecialVariable;
import edu.isi.webtools.objects.xml_objects.*;
import edu.isi.stella.*;

public class NameToLookup extends XMLObject {
    public String type;

  public static NameToLookup new_nameToLookup() {
    { NameToLookup self = null;

      self = new NameToLookup();
      self.cdataContent = null;
      self.textContent = null;
      self.type = null;
      return (self);
    }
  }

  public static GetAddressFromNameResponse getAddressFromName(NameToLookup name) {
    { String theName = name.textContent;

      { AreaCode SELF_000 = AreaCode.new_areaCode();

        SELF_000.textContent = "789";
        { AreaCode areaCode = SELF_000;

          { Exchange SELF_001 = Exchange.new_exchange();

            SELF_001.textContent = "654";
            { Exchange exchange = SELF_001;

              { Number SELF_002 = Number.new_number();

                SELF_002.textContent = "3210";
                { Number number = SELF_002;

                  { PhoneNumber SELF_003 = PhoneNumber.new_phoneNumber();

                    SELF_003.areaCode = areaCode;
                    SELF_003.exchange = exchange;
                    SELF_003.number = number;
                    { PhoneNumber phone = SELF_003;

                      { StreetNum SELF_004 = StreetNum.new_streetNum();

                        SELF_004.textContent = "919";
                        { StreetNum streetNum = SELF_004;

                          { StreetName SELF_005 = StreetName.new_streetName();

                            SELF_005.textContent = "Street for " + theName;
                            { StreetName streetName = SELF_005;

                              { City SELF_006 = City.new_city();

                                SELF_006.textContent = "Sunnytown";
                                { City city = SELF_006;

                                  { State SELF_007 = State.new_state();

                                    SELF_007.textContent = "UT";
                                    { State state = SELF_007;

                                      { Zip SELF_008 = Zip.new_zip();

                                        SELF_008.textContent = "43434";
                                        { Zip zip = SELF_008;

                                          { Stella_return SELF_009 = Stella_return.new_return();

                                            SELF_009.streetNum = streetNum;
                                            SELF_009.streetName = streetName;
                                            SELF_009.city = city;
                                            SELF_009.state = state;
                                            SELF_009.zip = zip;
                                            SELF_009.phone = phone;
                                            { Stella_return renamed_Return = SELF_009;

                                              { GetAddressFromNameResponse SELF_010 = GetAddressFromNameResponse.new_getAddressFromNameResponse();

                                                SELF_010.renamed_Return = renamed_Return;
                                                { GetAddressFromNameResponse result = SELF_010;

                                                  return (result);
                                                }
                                              }
                                            }
                                          }
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public static Stella_Object access_nameToLookup_Slot_Value(NameToLookup self, Symbol slotname, Stella_Object value, boolean setvalueP) {
    if (slotname == AddressExample.SYM_XSI_type) {
      if (setvalueP) {
        self.type = ((StringWrapper)(value)).wrapperValue;
      }
      else {
        value = StringWrapper.wrapString(self.type);
      }
    }
    else {
      { OutputStringStream STREAM_000 = OutputStringStream.newOutputStringStream();

        STREAM_000.nativeStream.print("`" + slotname + "' is not a valid case option");
        throw ((StellaException)(StellaException.newStellaException(STREAM_000.theStringReader()).fillInStackTrace()));
      }
    }
    return (value);
  }

  public Surrogate primaryType() {
    { NameToLookup self = this;

      return (AddressExample.SGT_ADDRESS_EXAMPLE_nameToLookup);
    }
  }

}

