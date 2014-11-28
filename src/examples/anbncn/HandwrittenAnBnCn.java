package examples.anbncn;

import apg.Parser;
import apg.Parser.UdtCallback;

class HandwrittenAnBnCn extends UdtCallback{
	HandwrittenAnBnCn(Parser p){super(p);}
	@Override public int callback(int offset){
		int as = 0;
		int bs = 0;
		int cs = 0;
		int i;
		for(i = offset; i < callbackData.inputString.length; i++){
			if(callbackData.inputString[i] == 'a'){as++;}
			else{break;}
		}
		for(; i < callbackData.inputString.length; i++){
			if(callbackData.inputString[i] == 'b'){bs++;}
			else{break;}
		}
		for(; i < callbackData.inputString.length; i++){
			if(callbackData.inputString[i] == 'c'){cs++;}
			else{break;}
		}
		if(as == 0 || as != bs || as != cs){return -1;}
		else{return as + bs + cs;}
	}
}
