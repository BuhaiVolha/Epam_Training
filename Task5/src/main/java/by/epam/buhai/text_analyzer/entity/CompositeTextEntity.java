package by.epam.buhai.text_analyzer.entity;

import by.epam.buhai.text_analyzer.parser.BaseParser;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import java.util.*;

public class CompositeTextEntity extends TextComponent {
    private List<TextComponent> childTextComponents;
    private TextComponentType type;
    private String value;

    private final static String SPACE = " ";
    private final static String NEW_LINE = "\n";
    private final static String TABULATION = "\t";
    private final static String PUNCT_WITH_SPACE_BEFORE = "[({-]";
    private final static String PUNCT_WITH_NO_SPACE_AFTER = "[({]";

    public CompositeTextEntity(TextComponentType type) {
        childTextComponents = new ArrayList<>();
        this.type = type;
    }

    public void accept(TextComponentVisitor visitor) {
        visitor.visit(this);
        acceptChildren(visitor);
    }

    protected void acceptChildren(TextComponentVisitor visitor) {
        childTextComponents.stream()
                .forEach(child -> child.accept(visitor));
    }


    public String getContent() {
        return value;
    }


    @Override
    public boolean isLeafTextEntity() {
        return childTextComponents.size() == 0;
    }


    @Override
    public Iterator<TextComponent> iterator() {
        return childTextComponents.iterator();
        //return new TextComponentIterator(this);
    }

//
//    @Override
//    public void setType(TextComponentType type) {
//        this.type = type;
//    }




    public void setChildTextComponents(List<TextComponent> childTextComponents) {
        this.childTextComponents = childTextComponents;
    }


    public int size() {
        //return childTextComponents.size() - 1;
        return childTextComponents.size();
    }


    @Override
    public TextComponentType getType() {
        return type;
    }


    public void addChildTextComponent(TextComponent textComponent) {
        childTextComponents.add(textComponent);
    }


    public void removeChildTextComponent(TextComponent textComponent) {
        childTextComponents.remove(textComponent);
    }


    public List<TextComponent> getChildTextComponents() {
        return childTextComponents;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        CompositeTextEntity that = (CompositeTextEntity) o;

        return new EqualsBuilder()
                .append(getChildTextComponents(), that.getChildTextComponents())
                .append(getType(), that.getType())
                .append(getContent(), that.getContent())
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getChildTextComponents())
                .append(getType())
                .append(getContent())
                .toHashCode();
    }


    @Override
    public String toString(){
        StringBuilder text = new StringBuilder();
        int top = childTextComponents.size();

        for (int i = 0; i < top; i++) {
            TextComponent childTextComponent = childTextComponents.get(i);

            switch (childTextComponent.getType()) {
                case NUMBER:
                case WORD:
                    if (i != top - 1) {
                        if (i > 0 && childTextComponents.get(i - 1).toString().matches(PUNCT_WITH_NO_SPACE_AFTER)) {
                            text.append(childTextComponent);
                        } else {
                            text.append(SPACE).append(childTextComponent);
                        }
                    }
                    break;
                case PARAGRAPH:
                    text.append(TABULATION).append(childTextComponent);
                    //text.append(childTextComponent);

                    if (i != top - 1) {
                        text.append(NEW_LINE);
                    }
                    break;
                case PUNCTUATION_CHAR:
                    if (childTextComponent.toString().matches(PUNCT_WITH_SPACE_BEFORE)) {
                        text.append(SPACE).append(childTextComponent);
                    } else {
                        text.append(childTextComponent);
                    }
                    break;
                case SENTENCE:
                case TEXT:
                case LETTER:
                    text.append(childTextComponent);
                    break;
            }
        }
        //return new String(text).trim();
        return new String(text);
    }
}
