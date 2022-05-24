package org.ascore.generateurs.ast;

import org.ascore.as.erreurs.ASError;
import org.ascore.ast.Ast;
import org.ascore.ast.buildingBlocs.Expression;
import org.ascore.ast.buildingBlocs.Statement;
import org.ascore.generateurs.lexer.TokenRule;
import org.ascore.tokens.Token;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AstGenerator {
    protected Hashtable<String, Ast<?>> programmesDict = new Hashtable<>();
    protected ArrayList<String> ordreProgrammes = new ArrayList<>();

    protected Hashtable<String, Ast<?>> expressionsDict = new Hashtable<>();
    protected ArrayList<String> ordreExpressions = new ArrayList<>();
    private int cptrExpr = 0;
    private int cptrProg = 0;

    public static void hasSafeSyntax(Token[] expressionArray) {
        int parentheses = 0;
        int braces = 0;
        int crochets = 0;

        for (Token token : expressionArray) {
            switch (token.getName()) {
                case "PARENT_OUV" -> parentheses++;
                case "PARENT_FERM" -> parentheses--;

                case "CROCHET_OUV" -> crochets++;
                case "CROCHET_FERM" -> crochets--;

                case "BRACES_OUV" -> braces++;
                case "BRACES_FERM" -> braces--;
            }
        }

        String pluriel = Math.abs(parentheses) > 1 ? "s" : "";

        //System.out.println(Arrays.toString(expressionArray));

        switch (Integer.compare(parentheses, 0)) {
            case -1 ->
                    throw new ASError.ErreurSyntaxe(-parentheses + " parenth\u00E8se" + pluriel + " ouvrante" + pluriel + " '(' manquante" + pluriel);
            case 1 ->
                    throw new ASError.ErreurSyntaxe(parentheses + " parenth\u00E8se" + pluriel + " fermante" + pluriel + " ')' manquante" + pluriel);
        }

        pluriel = Math.abs(braces) > 1 ? "s" : "";
        switch (Integer.compare(braces, 0)) {
            case -1 ->
                    throw new ASError.ErreurSyntaxe(-braces + " accolade" + pluriel + " ouvrante" + pluriel + " '{' manquante" + pluriel);
            case 1 ->
                    throw new ASError.ErreurSyntaxe(braces + " accolade" + pluriel + " fermante" + pluriel + " '}' manquante" + pluriel);
        }

        pluriel = Math.abs(crochets) > 1 ? "s" : "";
        switch (Integer.compare(crochets, 0)) {
            case -1 ->
                    throw new ASError.ErreurSyntaxe(-crochets + " crochet" + pluriel + " ouvrant" + pluriel + " '[' manquant" + pluriel);
            case 1 ->
                    throw new ASError.ErreurSyntaxe(crochets + " crochet" + pluriel + " fermant" + pluriel + " ']' manquant" + pluriel);
        }

    }

    public static Matcher sameStructureStatement(String line, String structurePotentielle) {
        //System.out.println(structurePotentielle.replaceAll("( ?)(#?)expression ?", Matcher.quoteReplacement("\\b.+")));
        //Pattern structurePattern = Pattern.compile(structurePotentielle.replaceAll("( ?)(#?)expression ?", Matcher.quoteReplacement("\\b.+")));
        // FIXME Maybe a catastrophic change idk
        Pattern structurePattern = Pattern.compile(structurePotentielle.replaceAll("( ?)(#?)expression ?", Matcher.quoteReplacement("\\b *([A-Z_] ?)+ *")));

        return structurePattern.matcher(line);
    }

    // TODO TEST!!!!!!
    public static Matcher sameStructureExpression(String line, String structurePotentielle) {
        Pattern structurePattern = Pattern.compile(structurePotentielle
                .replaceAll("#expression", Matcher.quoteReplacement("\\b.+"))
                .replaceAll("!expression *", Matcher.quoteReplacement("(?<!expression )"))
        );
        //System.out.println(line + " matcher:" + structurePattern.matcher(line));
        return structurePattern.matcher(line);
    }

    private ArrayList<String> addSubAstOrder(Hashtable<String, Ast<?>> sous_ast) {
        ArrayList<String> nouvelOrdre = new ArrayList<>(ordreExpressions);

        if (sous_ast.size() > 0) {
            for (String pattern : sous_ast.keySet()) {
                if (ordreExpressions.contains(pattern)) {
                    nouvelOrdre.remove(pattern);
                }
                int importance = sous_ast.get(pattern).getImportance();
                if (importance == -1) {
                    nouvelOrdre.add(pattern);
                } else {
                    if (nouvelOrdre.size() > importance && nouvelOrdre.get(importance) == null) {
                        nouvelOrdre.set(importance, pattern);
                    } else {
                        if (nouvelOrdre.size() < importance) nouvelOrdre.add(pattern);
                        else nouvelOrdre.add(importance, pattern);
                    }
                }
            }
            ordreExpressions.removeIf(Objects::isNull);
            //System.out.println(this.ordreExpressions);
        }
        return nouvelOrdre;
    }

    public Expression<?> evalOneExpr(ArrayList<Object> expressions, Hashtable<String, Ast<?>> sous_ast) {
        var result = eval(expressions, sous_ast);
        if (result.size() != 1) {
            throw new ASError.ErreurSyntaxe("Erreur ligne 106 dans AstGenerator");
        } else {
            return result.get(0);
        }
    }

    public ArrayList<Expression<?>> eval(ArrayList<Object> expressions, Hashtable<String, Ast<?>> sous_ast) {

        var regleSyntaxeDispo = new Hashtable<>(expressionsDict);
        var ordreRegleSyntaxe = new ArrayList<>(ordreExpressions);

        if (sous_ast != null) {
            regleSyntaxeDispo.putAll(sous_ast);
            ordreRegleSyntaxe = addSubAstOrder(sous_ast);
        }

        //System.out.println(expressions);
        if (expressions.size() == 0) {
            return new ArrayList<>();
        }
        // if the first expression is an arraylist, all the expressions are array lists
        if (expressions.get(0) instanceof ArrayList<?>) {
            ArrayList<Object> expressionList = new ArrayList<>();
            for (Object expr : expressions) {
                expressionList.addAll(eval((ArrayList<Object>) expr, regleSyntaxeDispo));
            }
            return expressionList.stream().map(e -> (Expression<?>) e).collect(Collectors.toCollection(ArrayList::new));
        }

        ArrayList<Object> expressionArray = new ArrayList<>(expressions);

        hasSafeSyntax(expressionArray.stream().filter(e -> e instanceof Token).toArray(Token[]::new));

        for (String regleSyntaxeEtVariante : ordreRegleSyntaxe) {
            String[] split = regleSyntaxeEtVariante.split("~");
            for (int idxVariante = 0; idxVariante < split.length; idxVariante++) {
                String regleSyntaxe = split[idxVariante];
                regleSyntaxe = regleSyntaxe.trim();

                List<String> membresRegleSyntaxe = Arrays.asList(regleSyntaxe.split(" "));
                int nbNotExpr = (int) membresRegleSyntaxe.stream().filter(e -> e.equals("!expression")).count();
                int longueurRegleSyntaxe = membresRegleSyntaxe.size() - nbNotExpr;

                int i = 0, debut, exprLength;
                while (i + longueurRegleSyntaxe <= expressionArray.size()) {

                    List<String> expressionNom = new ArrayList<>();

                    for (Object expr : expressionArray) {
                        expressionNom.add(expr instanceof Token token ? token.getName() : "expression");
                    }
                    Matcher match = sameStructureExpression(String.join(" ", expressionNom.subList(i, expressionNom.size())), regleSyntaxe);
                    if (regleSyntaxe.contains("#expression") && match.find()) {
                        if (match.start() != 0 || (expressionArray.get(i) instanceof Token && regleSyntaxe.startsWith("expression"))) {
                            i++;
                            continue;
                        }
                        debut = i;
                        expressionNom = expressionNom.subList(debut, expressionNom.size());
                        int fin = expressionNom.size();

                        List<Object> expr = expressionArray.subList(debut, fin);

                        Expression<?> capsule = (Expression<?>) expressionsDict
                                .get(regleSyntaxeEtVariante)
                                .apply(new ArrayList<>(expr), idxVariante);

                        ArrayList<Object> newArray = new ArrayList<>(expressionArray.subList(0, debut));
                        newArray.add(capsule);
                        newArray.addAll(expressionArray.subList(fin, expressionArray.size()));

                        expressionArray = newArray;

                    } else {
                        if (regleSyntaxe.contains("!expression") && i > 0 && expressionNom.get(i - 1).equals("expression")) {
                            i++;
                            continue;
                        }
                        debut = i;
                        exprLength = debut + longueurRegleSyntaxe;

                        if (sameStructureExpression(String.join(" ", expressionNom.subList(debut, exprLength)), regleSyntaxe).matches()) {
                            if ((regleSyntaxe.startsWith("expression") &&
                                    (!(expressionArray.get(debut) instanceof Expression<?>))
                                    ||
                                    expressionArray.get(debut) == null)
                            ) {
                                i++;
                                continue;
                            }
                            Expression<?> capsule = (Expression<?>) regleSyntaxeDispo
                                    .get(regleSyntaxeEtVariante)
                                    .apply(expressionArray.subList(debut, exprLength), idxVariante);

                            ArrayList<Object> newArray = new ArrayList<>(debut != 0 ? expressionArray.subList(0, debut) : new ArrayList<>());
                            newArray.add(capsule);
                            newArray.addAll(expressionArray.subList(debut + longueurRegleSyntaxe, expressionArray.size()));

                            expressionArray = newArray;
                            if (longueurRegleSyntaxe == 1) i++;

                        } else {
                            i++;
                        }
                    }
                }
            }
        }

        Token[] token = expressionArray.stream().filter(e -> e instanceof Token).toArray(Token[]::new);

        if (token.length > 0) {
            throw new ASError.ErreurSyntaxe("Expression ill\u00E9gale: " + String.join(" ", Arrays.stream(token).map(Token::getValue).toArray(String[]::new)));
        }

        //System.out.println(expressionArray);
        return ((ArrayList<?>) expressionArray).stream().map(e -> (Expression<?>) e).collect(Collectors.toCollection(ArrayList::new));
    }

    protected void reset() {
        expressionsDict.clear();
        programmesDict.clear();
        ordreExpressions.clear();
        ordreProgrammes.clear();
    }

    public ArrayList<String> getOrdreExpressions() {
        return ordreExpressions;
    }

    public ArrayList<String> getOrdreProgrammes() {
        return ordreProgrammes;
    }

    protected String remplaceCategoriesByMembers(String pattern) {
        String nouveauPattern = pattern;
        for (String option : pattern.split("~")) {
            for (String motClef : option.split(" ")) {  // on divise le pattern en mot clef afin d'evaluer ceux qui sont des categories (une categorie est entouree par des {})
                if (motClef.startsWith("{") && motClef.endsWith("}")) {  // on test si le mot clef est une categorie
                    ArrayList<String> membresCategorie = TokenRule.getMembreCategorie(motClef.substring(1, motClef.length() - 1)); // on va chercher les membres de la categorie (toutes les regles)
                    if (membresCategorie == null) {
                        throw new Error("La categorie: '" + pattern + "' n'existe pas");    // si la categorie n'existe pas, on lance une erreur
                    } else {
                        nouveauPattern = nouveauPattern.replace(motClef, "(" + String.join("|", membresCategorie) + ")");
                        // on remplace la categorie par les membres de la categorie
                        // pour ce faire, on entoure les membres dans des parentheses et on
                        // separe les membres par des |
                        // de cette facon, lorsque nous allons tester par regex si une ligne correspond
                        // a un programme ou une expression, la categorie va "matcher" avec
                        // tous les membres de celle-ci
                    }
                }
            }
        }
        return nouveauPattern;  // on retourne le pattern avec les categories changees
    }

    protected void addStatement(String pattern, Ast<? extends Statement> fonction) {
        //for (String programme : pattern.split("~")) {
        var sousAstCopy = new Hashtable<>(fonction.getSousAst());
        for (String p : sousAstCopy.keySet()) {
            fonction.getSousAst().remove(p);
            fonction.getSousAst().put(remplaceCategoriesByMembers(p), sousAstCopy.get(p));
        }
        if (fonction.getImportance() == -1)
            fonction.setImportance(cptrProg++);
        String nouveauPattern = remplaceCategoriesByMembers(pattern);
        var previous = programmesDict.put(nouveauPattern, fonction); // remplace les categories par ses membres, s'il n'y a pas de categorie, ne modifie pas le pattern
        if (previous == null) {
            ordreProgrammes.add(fonction.getImportance(), nouveauPattern);
        }
        //}
    }

    protected void addStatement(String pattern, Function<List<Object>, ? extends Statement> fonction) {
        var ast = Ast.from(fonction);
        //for (String programme : pattern.split("~")) {
        ast.setImportance(cptrProg++);
        String nouveauPattern = remplaceCategoriesByMembers(pattern);
        var previous = programmesDict.put(nouveauPattern, ast); // remplace les categories par ses membres, s'il n'y a pas de categorie, ne modifie pas le pattern
        if (previous == null) {
            ordreProgrammes.add(nouveauPattern);
        }
        //}
    }

    protected void addStatement(String pattern, BiFunction<List<Object>, Integer, ? extends Statement> fonction) {
        var ast = Ast.from(fonction);
        //for (String programme : pattern.split("~")) {
        ast.setImportance(cptrProg++);
        String nouveauPattern = remplaceCategoriesByMembers(pattern);
        var previous = programmesDict.put(nouveauPattern, ast); // remplace les categories par ses membres, s'il n'y a pas de categorie, ne modifie pas le pattern
        if (previous == null) {
            ordreProgrammes.add(nouveauPattern);
        }
        //}
    }

    protected void addExpression(String pattern, Ast<? extends Expression<?>> fonction) {
        String nouveauPattern = remplaceCategoriesByMembers(pattern);
        if (fonction.getImportance() == -1)
            fonction.setImportance(cptrExpr++);
        var previous = expressionsDict.put(nouveauPattern, fonction);
        if (previous == null) {
            ordreExpressions.add(fonction.getImportance(), nouveauPattern);
        }
    }

    protected void addExpression(String pattern, Function<List<Object>, ? extends Expression<?>> fonction) {
        var ast = Ast.from(fonction);
        String nouveauPattern = remplaceCategoriesByMembers(pattern);
        ast.setImportance(cptrExpr++);
        var previous = expressionsDict.put(nouveauPattern, ast);
        if (previous == null) {
            ordreExpressions.add(nouveauPattern);
        }
    }

    protected void addExpression(String pattern, BiFunction<List<Object>, Integer, ? extends Expression<?>> fonction) {
        var ast = Ast.from(fonction);
        String nouveauPattern = remplaceCategoriesByMembers(pattern);
        ast.setImportance(cptrExpr++);
        var previous = expressionsDict.put(nouveauPattern, ast);
        if (previous == null) {
            ordreExpressions.add(nouveauPattern);
        }
    }

    protected void setOrdreProgramme() {
        for (int i = 0; i < programmesDict.size(); ++i) {
            ordreProgrammes.add(null);
        }
        for (String pattern : programmesDict.keySet()) {
            int importance = programmesDict.get(pattern).getImportance();
            if (importance == -1) {
                ordreProgrammes.add(pattern);
            } else {
                //if (ordreProgrammes.get(importance) == null) {
                //    ordreProgrammes.set(importance, pattern);
                //} else {
                //    ordreProgrammes.add(importance, pattern);
                //}
                ordreProgrammes.add(importance, pattern);
            }
        }
        ordreProgrammes.removeIf(Objects::isNull);
        //System.out.println(this.ordreProgrammes);
    }

    protected void setOrdreExpression() {
        for (int i = 0; i < expressionsDict.size(); ++i) {
            ordreExpressions.add(null);
        }
        for (String pattern : expressionsDict.keySet()) {
            int importance = expressionsDict.get(pattern).getImportance();
            if (importance == -1) {
                ordreExpressions.add(pattern);
            } else {
                if (ordreExpressions.get(importance) == null) {
                    ordreExpressions.set(importance, pattern);
                } else {
                    ordreExpressions.add(importance, pattern);
                }
            }
        }
        ordreExpressions.removeIf(Objects::isNull);
        //System.out.println(this.ordreExpressions);
    }

    public Statement parse(List<Token> listToken) {

        var programmeEtIdxVariante = getStatementOrThrow(listToken);
        int idxVariante = programmeEtIdxVariante.getKey();
        String programmeEtVariante = programmeEtIdxVariante.getValue();
        String programme = programmeEtVariante.split("~")[idxVariante];

        var expressions_programme = getDivisionExpressionsStatement(listToken, programmeEtVariante, idxVariante);

        var expressions = expressions_programme.getValue();
        var programmeToken = expressions_programme.getKey();


        var arbre = eval(
                expressions.stream().map(e -> (Object) e).collect(Collectors.toCollection(ArrayList::new)),
                programmesDict.get(programmeEtVariante).getSousAst()
        );

        ArrayList<Object> finalLine = new ArrayList<>(Arrays.asList(programme.split(" ")));


        Iterator<?> expressionIt = arbre.iterator();
        Iterator<Token> programmeIt = programmeToken.iterator();

        ArrayList<Object> finalizedLine = new ArrayList<>();

        try {
            for (Object e : finalLine) {
                if (e.equals("expression")) {
                    finalizedLine.add(expressionIt.next());
                } else if (e.equals("#expression")) {
                    expressionIt.forEachRemaining(finalizedLine::add);
                } else {
                    finalizedLine.add(programmeIt.next());
                }
            }
        } catch (NoSuchElementException e) {
            throw new ASError.ErreurSyntaxe("Syntaxe invalide. " + e);
        }

        //System.out.println(finalLine);
        if (expressionIt.hasNext()) {
            throw new ASError.ErreurSyntaxe("Syntaxe invalide. Est-ce qu'il manque une virgule entre deux \u00E9l\u00E9ments?");
        }

        return (Statement) programmesDict
                .get(programmeEtVariante)
                .apply(finalizedLine, idxVariante);
    }

    /**
     * @param listToken the list of token making the lexed line
     * @return an entry composed of the variant idx as the key and the programme as the value
     * @throws ASError.ErreurSyntaxe if there are no programme that match the tokens in listToken
     */
    public Map.Entry<Integer, String> getStatementOrThrow(List<Token> listToken) {
        String programmeTrouve = null;
        List<String> structureLine = new ArrayList<>();
        listToken.forEach(e -> structureLine.add(e.getName()));
        int idxVariante = 0;

        int nbTokenProgrammeTrouvee = 0;
        for (String programme : ordreProgrammes) {
            //System.out.println(programme + " " + structureLine);
            String[] variantes = programme.split("~");
            for (int i = 0; i < variantes.length; i++) {
                String variante = variantes[i];
                if (sameStructureStatement(String.join(" ", structureLine), variante).matches()) {
                    int nbTokenProgrammeAlter = variante.replaceAll("#?expression", "").replaceAll("(\\(.+\\))|(\\w+)", "T").length();
                    if (programmeTrouve == null || nbTokenProgrammeTrouvee < nbTokenProgrammeAlter) {
                        programmeTrouve = programme;
                        idxVariante = i;
                        nbTokenProgrammeTrouvee = nbTokenProgrammeAlter;
                    }
                }
            }
        }
        if (programmeTrouve == null) {
            throw new ASError.ErreurSyntaxe("Syntaxe invalide: " + listToken
                    .stream()
                    .map(Token::getValue)
                    .toList()
            );
        }
        return Map.entry(idxVariante, programmeTrouve);
    }

    /**
     * @param listToken
     * @param programme
     * @param idxVariante
     * @return map: key=programme, value=expressions
     */
    private Map.Entry<ArrayList<Token>, ArrayList<ArrayList<Token>>> getDivisionExpressionsStatement(List<Token> listToken, String programme, Integer idxVariante) {
        programme = programme.split("~")[idxVariante];
        ArrayList<String> structureLine = new ArrayList<>();
        listToken.forEach(e -> structureLine.add(e.getName()));

        ArrayList<String> structureProgramme = new ArrayList<>(Arrays.asList(programme.split(" ")));
        // structureProgramme.removeIf(e -> e.equals("expression") || e.equals("#expression"));
        Iterator<String> iterProgramme = structureProgramme.iterator();

        ArrayList<ArrayList<Token>> expressionsList = new ArrayList<>();
        ArrayList<Token> programmeList = new ArrayList<>();

        if (programme.contains("expression") || programme.contains("#expression")) {
            String clef = iterProgramme.hasNext() ? iterProgramme.next() : "";

            ArrayList<Token> expressionList = new ArrayList<>();

            for (int i = 0; i < structureLine.size(); ++i) {
                // FIXME Maybe a catastrophic change idk part 2
                if (clef.equals("expression") || clef.equals("#expression")) {
                    expressionList.add(listToken.get(i));
                    clef = iterProgramme.hasNext() ? iterProgramme.next() : "";
                    continue;
                }
                if (!clef.isBlank() && structureLine.get(i).matches(clef)) {
                    clef = iterProgramme.hasNext() ? iterProgramme.next() : "";

                    programmeList.add(listToken.get(i));
                    expressionsList.add(expressionList);
                    expressionList = new ArrayList<>();
                } else {
                    expressionList.add(listToken.get(i));
                }
            }
            expressionsList.add(expressionList);
            expressionsList.removeIf(ArrayList::isEmpty);
        } else {
            programmeList = new ArrayList<>(listToken);
        }
        return Map.entry(programmeList, expressionsList);
    }
}

























