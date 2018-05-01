package by.tc.task01.dao.creator.withSettingFields;

import by.tc.task01.entity.Sellable;
import by.tc.task01.entity.Oven;
import by.tc.task01.entity.criteria.Parameters;

import java.util.Map;

public class OvenCommand extends Command {
    @Override
    public Sellable createGoodsWith(Map<String, String> parameters) {
        Oven oven = new Oven();

        oven.setCapacity(Double.parseDouble(parameters.get(Parameters.GoodsType.Oven.CAPACITY.toString())));
        oven.setDepth(Double.parseDouble(parameters.get(Parameters.GoodsType.Oven.DEPTH.toString())));
        oven.setWeight(Double.parseDouble(parameters.get(Parameters.GoodsType.Oven.WEIGHT.toString())));
        oven.setHeight(Double.parseDouble(parameters.get(Parameters.GoodsType.Oven.HEIGHT.toString())));
        oven.setPowerConsumption(Double.parseDouble(parameters.get(Parameters.GoodsType.Oven.POWER_CONSUMPTION.toString())));
        oven.setWidth(Double.parseDouble(parameters.get(Parameters.GoodsType.Oven.WIDTH.toString())));

        return oven;
    }
}
