package hash.table;

import hash.table.table.HashTable;

public class Main {
    public static void main(String[] args) {
        HashTable<String, String> table = new HashTable<>();
        table.put("Автомобиль", " механический транспорт, обычно с четырьмя колесами, который используется для перевозки людей и грузов");
        table.put("Мотоцикл", " транспортное средство с двумя колесами, которое управляется мотоциклистом и обычно приводится в движение двигателем внутреннего сгорания.");
        table.put("Автобус", " длинное транспортное средство, предназначенное для перевозки большого количества людей по маршруту.");
        table.put("Трамвай", " транспортное средство, которое передвигается по рельсам по определенному маршруту и обычно используется для перевозки людей в городе.");
        table.put("Метро", " подземный железнодорожный транспорт, который обычно используется для перевозки людей в городе.");
        table.put("Поезд", " транспортное средство, которое передвигается по рельсам и используется для перевозки людей и грузов.");
        table.put("Троллейбус", " транспортное средство, которое передвигается по определенному маршруту и получает электроэнергию от подвесной линии.");
        table.put("Самолет ", " транспортное средство, которое летает в воздухе и обычно используется для перевозки людей и грузов на большие расстояния.");
        table.put("Вертолет", " транспортное средство, которое летает в воздухе и способно вертикально взлетать и приземляться.");
        table.put("Корабль", " транспортное средство, которое движется по воде и используется для перевозки людей и грузов.");
        table.put("Катер", " маленькое транспортное средство, которое движется по воде и обычно используется для перевозки людей на небольшие расстояния.");
        table.put("Яхта", " большое транспортное средство, которое движется по воде и обычно используется для развлечения и отдыха.");
        table.put("Велосипед", " транспортное средство, которое передвигается на колесах и обычно управляется педалями.");
        table.put("Электросамокат", " транспортное средство, которое передвигается на колесах и получает электроэнергию от батареи.");
        table.put("Скейтборд", " транспортное средство, которое передвигается на колесах и обычно управляется телом.");
        table.put("Ролики", " транспортное средство, которое передвигается на колесах и обычно надевается на ноги.");
        table.put("Электроавтомобиль", "механический транспорт, который использует электрический двигатель вместо двигателя внутреннего сгорания.");
        table.put("Скоростной поезд", "поезд, который может достигать очень высокой скорости и обычно используется для перевозки людей на большие расстояния");
        table.put("Монорельс", "транспортное средство, которое передвигается по одной рельсе и обычно используется для перевозки людей в городе");
        table.put("Гидросамолет", "транспортное средство, которое может двигаться как по воздуху, так и по воде, и обычно используется для перевозки людей и грузов в местах, где нет аэродромов или портов.");
        System.out.println(table);
        System.out.println("Table size: "+ table.size());

        System.out.println("Key: 'Вертолет'" + table.get("Вертолет")+"\n");

        table.remove("Вертолет");
        System.out.println(table);
        System.out.println("Table size: "+ table.size());
        System.out.println(table.get("Вертолет"));    // null

        table.put("Мотодельтаплан", "легкий летательный аппарат, передвигающийся по воздуху под действием двигателя и механического воздействия пилота, который обычно используется для рекреации и смотрящих за землей.");
        table.put("Электричка", "это пригородный транспорт, который движется по железнодорожным путям, подавая питание через контактную сеть, и останавливается на станциях.");
        table.put("Теплоход", "это водный транспорт, который движется по рекам, морям и океанам, используя двигатель, работающий на топливе, либо на пару, либо на газ.");
        table.put("Скутер", "это мотоцикл с малой мощностью, предназначенный для перевозки одного или двух человек, и обычно используется для перемещения в городской среде.");
        table.put("Грузовик", "это автомобиль, предназначенный для перевозки грузов и товаров на дальние и ближние расстояния.");
        System.out.println(table);
        System.out.println("Table size: "+ table.size());
    }
}