package interpreteur.as;


import interpreteur.generateurs.lexer.LexerGenerator;
import interpreteur.generateurs.lexer.LexerLoader;

/**
 * 
 * Les explications vont être rajouté quand j'aurai la motivation de les écrire XD
 * @author Mathis Laroche
 */ 
public class ASLexer extends LexerGenerator {
	public ASLexer() {
        super();
        LexerLoader loader = new LexerLoader("interpreteur/regle_et_grammaire/ExempleGrammaire.yaml");
        loader.load();
        sortRegle();
    }
}
