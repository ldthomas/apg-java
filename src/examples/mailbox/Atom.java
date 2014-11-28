package examples.mailbox;

import apg.Parser;
import apg.Parser.UdtCallback;

class Atom extends UdtCallback{
//  ;Atom            = 1*atext	
//	;atext           =   ALPHA / DIGIT /    ; Printable US-ASCII
//	;                       "!" / "#" /     ; characters not including
//	;                       "$" / "%" /     ; specials.  Used for atoms.
//	;                       "&" / "'" /
//	;                       "*" / "+" /
//	;                       "-" / "/" /
//	;                       "=" / "?" /
//	;                       "^" / "_" /
//	;                       "`" / "{" /
//	;                       "|" / "}" /
//	;                       "~"
	Parser parser;
	Atom(Parser parser){super(parser);}
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
				else if(c == 33){digits++;}
				else if(c >= 35 && c <= 39){digits++;}
				else if(c == 42 || c == 43){digits++;}
				else if(c == 45){digits++;}
				else if(c == 47){digits++;}
				else if(c == 61){digits++;}
				else if(c == 63){digits++;}
				else if(c >= 94 && c <= 96){digits++;}
				else if(c >= 123 && c <= 126){digits++;}
				else{break;}
			}
			if(digits == 0){break;}
			len += digits;
		}
		return (len == 0) ? -1 : len;
	}
}
