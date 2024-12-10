grammar InlineQueryGrammar;

@header {
package ru.itmo.nerc.vcb.bot.chat.antlr;
}

inlineQuery returns [String json = "{";] @init {}
    : (ignoreParameter ';' WS*)* ignoreParameter {$json += "}";}
    | ((ignoreParameter ';' WS*)* anyParameter {$json += $anyParameter.json + ",";} ';' WS*)* 
      (ignoreParameter ';' WS*)* anyParameter {$json += $anyParameter.json + "}";} ';'?  
    | ((ignoreParameter ';' WS*)* anyParameter {$json += $anyParameter.json + ",";} ';' WS*)* 
      (ignoreParameter ';' WS*)* anyParameter {$json += $anyParameter.json;} ';' WS* 
      (ignoreParameter ';' WS*)* ignoreParameter ';'? {$json += "}";}
    ; 
    
anyParameter returns [String json = "";] @init {}
    : taskParameter {
        $json = $taskParameter.json;
    }
    | groupsParameter {
        $json = $groupsParameter.json;
    }
    | typeParameter {
        $json = $typeParameter.json;
    }
    | idParameter {
        $json = $idParameter.json;
    }
    | answerParameter {
        $json = $answerParameter.json;
    }
    | keyParameter {
        $json = $keyParameter.json;
    }
    | valueParameter {
        $json = $valueParameter.json;
    }
    ;
    
taskParameter returns [String json] @init {}
    : taskOperator sentenceWithComma? {
        $json = "\"task\":\"" + ($sentenceWithComma.text != null ? $sentenceWithComma.text.trim () : "") + "\"";
    }
    ;
    
taskOperator
    : '???'
    | 't'
    | 'task'
    | 'Task'
    | 'задача'
    | 'Задача'
    ;
    
groupsParameter returns [String json] @init {}
    : groupsOperator {
        $json = "\"groups\":";
    }
    (sentencesArray {$json += $sentencesArray.array;})?
    ;
   
groupsOperator
    : 'h'
    | 'g'
    | 'halls'
    | 'groups'
    | 'Halls' 
    | 'Groups'
    | 'холлы'
    | 'Холлы'
    ;
    
typeParameter returns [String json] @init {}
    : typeOperator sentence? {
        $json = "\"type\":\"" + ($sentence.text != null ? $sentence.text.trim () : "") + "\"";
    }
    ;
    
typeOperator
    : 'type'
    | 'тип'
    ;
    
idParameter returns [String json] @init {}
    : idOperator sentence? {
        $json = "\"id\":" + ($sentence.text != null ? $sentence.text.trim () : "");
    }
    ;
    
idOperator
    : 'id'
    | 'ID'
    ;
    
answerParameter returns [String json] @init {}
    : answerOperator sentenceWithComma? {
        $json = "\"answer\":\"" + ($sentenceWithComma.text != null ? $sentenceWithComma.text.trim () : "") + "\"";
    }
    ;
    
answerOperator
    : 'answer'
    | 'ответ'
    ;
    
keyParameter returns [String json] @init {}
    : keyOperator sentenceWithComma? {
        $json = "\"key\":\"" + ($sentenceWithComma.text != null ? $sentenceWithComma.text.trim () : "") + "\"";
    }
    ;
    
keyOperator
    : 'key'
    | 'Key'
    | 'ключ'
    | 'Ключ'
    ;
    
valueParameter returns [String json] @init {}
    : valueOperator sentenceWithComma? {
        $json = "\"value\":\"" + ($sentenceWithComma.text != null ? $sentenceWithComma.text.trim () : "") + "\"";
    }
    ;
    
valueOperator
    : 'value'
    | 'Value'
    | 'значение'
    | 'Значение'
    ;
    
ignoreParameter
    : (ANY | WS)*
    ;

sentencesArray returns [String array = "[";] @init {}
    : (sentence {$array += "\"" + $sentence.text.trim () + "\",";} ',')*
    (sentence {$array += "\"" + $sentence.text.trim () + "\"]";} | WS* EOF {$array += "]";})
    ;

sentenceWithComma
    : (sentence | ',')+
    ;

sentence
    : (WS+ | ANY+ | taskOperator | groupsOperator | typeOperator | idOperator | answerOperator | keyOperator | valueOperator)+
    ;

WS: ' ' | '\n' | '\t';
ANY: ~(',' | ';');
