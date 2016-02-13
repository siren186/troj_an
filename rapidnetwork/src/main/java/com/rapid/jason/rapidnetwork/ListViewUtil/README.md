# ListViewUtil

标签：ListViewUtil

## 概括
> ListViewUtil 是一款通用的列表适配器，可以方便的生成列表，无需繁杂的书写viewhold。

## 使用说明
### 1、创建Adapter继承BaseCardsAdapter

### 2、在初始化函数传入listview的id和context。
```java
super(context, R.layout.card_layout);
```

### 3、实现setViewHolder函数
```java
@Override
public void setViewHolder(int position, BaseViewHolder baseViewHolder) {
    //通过getview函数传入控件id获取就行了。
    TextView textView = baseViewHolder.getView(R.id.tv_filename);
}
```