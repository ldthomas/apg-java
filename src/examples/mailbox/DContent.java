/*  ******************************************************************************
    Copyright (c) 2021, Lowell D. Thomas
    All rights reserved.
    
    This file is part of Java APG Version 1.1.0.
    Java APG Version 1.1.0 may be used under the terms of the 2-Clause BSD License.
    
*   ******************************************************************************/
package examples.mailbox;

import apg.Parser;
import apg.Parser.UdtCallback;

class DContent extends UdtCallback {
//  ;u_dcontent     = 1*dcontent
//	;dcontent       = %d33-90 / ; Printable US-ASCII
//	;                  %d94-126 ; excl. "[", "\", "]"

    DContent(Parser parser) {
        super(parser);
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
                c = (int) in[i];
                if ((c >= 33 && c <= 90)
                        || (c >= 94 && c <= 126)) {
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
        return (len == 0) ? -1 : len;
    }
}
