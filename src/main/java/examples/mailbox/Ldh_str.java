/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples.mailbox;

import apg.Parser;
import apg.Parser.UdtCallback;

class Ldh_str extends UdtCallback {
//	;e_Ldt-str = *(Ldh-str)
//	;u_Ldt-str = 1*(Ldh-str)
//	;Ldh-str   = *"-" 1*Let-dig

    private final boolean optional;

    public Ldh_str(Parser parser) {
        super(parser);
        this.optional = true;
    }

    public Ldh_str(Parser parser, boolean optional) {
        super(parser);
        this.optional = optional;
    }

    @Override
    public int callback(int offset) {
        int len = 0;
        int i = offset;
        char[] in = callbackData.inputString;
        int inlen = callbackData.inputString.length;
        int c;
        while (true) {
            int digits = 0;
            for (; i < inlen; i++) {
                if (callbackData.inputString[i] == '-') {
                    digits++;
                } else {
                    break;
                }
            }
            for (; i < inlen; i++) {
                c = (int) in[i];
                if ((c >= 97 && c <= 122)
                        || (c >= 65 && c <= 90)
                        || (c >= 48 && c <= 57)) {
                    digits++;
                } else {
                    break;
                }
            }
            if (digits == 0) {
                break;
            }
            len += digits;
        }
        if (!optional && len == 0) {
            len = -1;
        }
        return len;
    }
}
