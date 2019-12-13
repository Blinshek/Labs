package com.company;

abstract class Character implements ActionsWithItems {
    private String name;
    private int age;
    private Enum sex;
    private Enum charCondition;
    protected Item[] Items = new Item[10];
    protected int[] ItemCount = new int[Items.length];

    public Character() {
        name = "Nameless";
        age = 0;
        sex = Sex.NOT_STATED;
    }

    public Character(String name, int age, Enum sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex.toString();
    }

    public void setSex(Enum sex) {
        this.sex = sex;
    }

    public Enum getCharCondition() {
        return charCondition;
    }

    public void setCharCondition(Enum charCondition) {
        this.charCondition = charCondition;
    }

    @Override
    public void take(Item takedItem, int count) {
        int freeSpaceCell = -1;
        boolean HasItem = false;

        for (int i = 0; i < Items.length; i++) //Поиск такого предмета в инвенторе
            //Если в инвенторе уже есть такой предмет
            if ((Items[i] != null) && (Items[i].equals(takedItem))) {
                ItemCount[i] += count;
                HasItem = true;
                break;
            }

        //Если такого предмета ещё нет в инвенторе
        if (!HasItem) {
            for (int i = 0; i < Items.length; i++)
                if (Items[i] == null) {
                    freeSpaceCell = i;
                    break;
                }

            if (freeSpaceCell != -1) {
                Items[freeSpaceCell] = takedItem;
                ItemCount[freeSpaceCell] += count;
                HasItem = true;
            } else
                System.out.println("Инвентарь полон");
        }

        if (HasItem)
            System.out.println(getName() + " взял " + takedItem.toString() + " x" + count);
    }

    @Override
    public void give(Item item, Character to, int count) {
        int itemsInInventory = ItemCount[getItemIndex(item)];
        int tradedItemsCnt = Math.min(count, itemsInInventory);

        System.out.println(this.getName() + " передал " + to.getName() + " " + item + " x" + tradedItemsCnt);
        to.take(item, tradedItemsCnt);
        this.ItemCount[getItemIndex(item)] -= tradedItemsCnt;

        if (this.ItemCount[getItemIndex(item)] == 0)
            this.Items[getItemIndex(item)] = null;
    }

    void removeItem(Item itemThatMustBeKilled) {
        this.removeItem(itemThatMustBeKilled, 1);
    }

    void removeItem(Item itemThatMustBeKilled, int count) {
        int index = getItemIndex(itemThatMustBeKilled);
        int trueCount = Math.min(count, ItemCount[index]);
        ItemCount[index] -= trueCount;
        if (ItemCount[index] == 0)
            Items[index] = null;
        //System.out.println(this + " выбрасывает " + itemThatMustBeKilled + " x" + trueCount);
    }

    //Вывод инвенторя
    public void printInventory() {
        int symvcnt = 0;
        int itemsCountLen = 0;

        for (int i = 0; i < Items.length; i++)
            if (Items[i] != null) {
                if (Items[i].toString().length() > symvcnt)
                    symvcnt = Items[i].toString().length();
                if (("" + ItemCount[i]).length() > itemsCountLen)
                    itemsCountLen = ("" + ItemCount[i]).length();
            }

        //Вывод на экран
        boolean InvIsEmpty = true;
        for (int i = 0; i < Items.length; i++)
            if ((Items[i] != null)) {
                InvIsEmpty = false;
                System.out.println("+" + Main.repeat(symvcnt + 4, "-") + "+" + Main.repeat(itemsCountLen + 2, "-") + "+");
                System.out.println("| " + String.format("%-" + (symvcnt + 3) + "s", Items[i].toString()) + "| " + String.format("%-" + (itemsCountLen + 1) + "s", ItemCount[i]) + "|");
            }
        if (!InvIsEmpty)
            System.out.println("+" + Main.repeat(symvcnt + 4, "-") + "+" + Main.repeat(itemsCountLen + 2, "-") + "+");
    }

    public int getItemIndex(Item item) {
        for (int i = 0; i < this.Items.length; i++)
            if (Items[i].equals(item))
                return i;
        return -1;
    }

    void say(String phrase) {
        say(phrase, 0);
    }

    abstract void say(String phrase, int delayMillis);

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        return (name.hashCode() + sex.hashCode() + age) * 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() == obj.getClass())
            return super.hashCode() == obj.hashCode();
        return false;
    }
}