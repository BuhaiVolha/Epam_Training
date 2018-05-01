package by.tc.task01.dao.creator.withSettingFields;

import by.tc.task01.entity.Sellable;
import by.tc.task01.entity.TextBook;
import by.tc.task01.entity.criteria.Parameters;

import java.util.Map;

public class TextBookCommand extends Command {
    @Override
    public Sellable createGoodsWith(Map<String, String> parameters) {
        TextBook textBook = new TextBook();

        textBook.setAuthor(parameters.get(Parameters.GoodsType.TextBook.AUTHOR.toString()));
        textBook.setNumberOfPages(Double.parseDouble(parameters.get(Parameters.GoodsType.TextBook.NUMBER_OF_PAGES.toString())));
        textBook.setSubject(parameters.get(Parameters.GoodsType.TextBook.SUBJECT.toString()));
        textBook.setTitle(parameters.get(Parameters.GoodsType.TextBook.TITLE.toString()));

        return textBook;
    }
}
