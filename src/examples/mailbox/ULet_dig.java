package examples.mailbox;

import apg.Parser;
import apg.Parser.UdtCallback;

class ULet_dig extends UdtCallback{
//	;u_Let-dig      = 1*Let-dig
//	;Let-dig        = ALPHA / DIGIT
	ULet_dig(Parser parser){super(parser);}
	@Override public int callback(int offset){
		int len = 0;
		int i = offset;
		char[] in = callbackData.inputString;
		int inlen = callbackData.inputString.length;
		int c;
		while(true){
			int digits = 0;
			for(; i < inlen; i++){
				c = (int)in[i];
				if((c >= 97 && c <= 122) ||
						(c >= 65 && c <= 90) ||
						(c >= 48 && c <= 57)){digits++;}
				else{break;}
			}
			if(digits == 0){break;}
			len += digits;
		}
		return (len == 0) ? -1 : len;
	}
}
