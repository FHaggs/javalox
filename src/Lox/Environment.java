package Lox;

import java.util.HashMap;
import java.util.Map;

class Environment {
    private Environment enclosing;
    private final Map<String, Object> values = new HashMap<>();
    private final Object UNINITIALIZED = new Object();

    Environment() {
        enclosing = null;
      }
    
      Environment(Environment enclosing) {
        this.enclosing = enclosing;
      }

    public Object get(Token name) {
        if (values.containsKey(name.lexeme)) {
            Object obj = values.get(name.lexeme);
            if (obj == UNINITIALIZED){
                throw new RuntimeError(name, "Variable '" + name.lexeme + "' is not initialized.");
            }
            return obj;
        }
        
        if (enclosing != null) return enclosing.get(name);
        
        throw new RuntimeError(name,
                "Undefined variable '" + name.lexeme + "'.");
    }
    public void assign(Token name, Object value){
        if(values.containsKey(name.lexeme)){
            values.put(name.lexeme, value);
            return;
        }
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
          }
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }

    public void define(String name, Object obj) {
        if (obj == null){
            obj = UNINITIALIZED;
        }
        values.put(name, obj);
    }

}