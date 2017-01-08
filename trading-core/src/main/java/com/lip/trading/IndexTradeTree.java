package com.lip.trading;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lip on 2016-12-12 22:40
 * 迷你股指二叉树模型( 目标 AVL 树) 左节点的price总是小于右节点
 * 根节点为最新价
 */
public class IndexTradeTree {
    public long id;//订单id
    public IndexTradeModel indexTrade;//二叉树数据
    public double price;//订单对应的平仓价格
    public IndexTradeTree leftTrade;// 左节点
    public IndexTradeTree rightTrade;// 右节点
    public IndexTradeTree siblingTrade;//兄弟节点
    public IndexTradeTree parentTrade;//父亲节点
    private int size=0;

    private IndexTradeTree(long _id, IndexTradeModel _indexTrade, double _price) {
        this.id = _id;
        this.indexTrade = _indexTrade;
        this.price = _price;
    }

    /**
     * 初始化订单树的时候调用
     * @param price
     * @return
     */
    public static IndexTradeTree getRootTree(double price) {
        return new IndexTradeTree(0, null, price);
    }

    /**
     * 根据节点的最新价格将节点插入根节点中
     *
     * @param indexTree
     */
    private synchronized void add(IndexTradeTree indexTree) {
        if (indexTree == null)
            return;
        if (indexTree.price > this.price)//应该插入右节点
        {
            //TODO 改为非递归
            if (this.rightTrade != null) {
                this.rightTrade.add(indexTree);
            } else {
                this.rightTrade = indexTree;
                indexTree.parentTrade = this;
            }
        } else if (indexTree.price < this.price)//插入左节点
        {
            //TODO 改为非递归
            if (this.leftTrade != null) {
                this.leftTrade.add(indexTree);
            } else {
                this.leftTrade = indexTree;
                indexTree.parentTrade = this;
            }
        } else {//兄弟节点
            if(this.siblingTrade!=null)
            {
                this.siblingTrade.add(indexTree);
            }else{
                this.siblingTrade=this;
            }
        }
        size++;
    }

    /**
     * 删除节点
     *
     * @param indexTree
     */
    public synchronized void remove(IndexTradeTree indexTree) {
        if (indexTree == null)
            return;
        if (indexTree.price > this.price) {
            //TODO 改为非递归
            if (this.rightTrade != null) {
                this.rightTrade.remove(indexTree);
            }
        } else if (indexTree.price < this.price) {
            if (this.leftTrade != null) {
                this.leftTrade.remove(indexTree);
            }
        } else {//移除该节点
            IndexTradeTree parentTree = this.parentTrade;
            IndexTradeTree leftTree = this.leftTrade;
            IndexTradeTree rightTree = this.rightTrade;
            if (parentTree.leftTrade == this)//移除的是根节点的左节点
            {
                parentTree.leftTrade = null;
            } else {//移除的是根节点的右节点
                parentTree.rightTrade = null;
            }
            parentTree.add(leftTree);
            parentTree.add(rightTree);
        }
        size--;
    }


    /**
     * 移除左节点订单号为id的节点
     * @param id 订单id
     * @param _price 平仓价格
     */
    public synchronized void remove(long id,double _price) {
        if (this.id == id) {
            IndexTradeTree parentTree = this.parentTrade;
            //重构子树结构
            List<IndexTradeTree> list = new ArrayList<>();
            if (this.leftTrade != null) {
                list.addAll(this.leftTrade.toTreeList());
            }
            if (this.rightTrade != null) {
                list.addAll(this.rightTrade.toTreeList());
            }
            if (parentTree.leftTrade.equals(this))//移除的是父节点的左孩子
            {
                parentTree.leftTrade = getTreeFromList(list);
            }else{//移除的是父节点的右孩子
                parentTree.rightTrade = getTreeFromList(list);
            }
            size--;
        }else if(this.price>_price){//左节点
            if(this.leftTrade!=null)
            {
                this.leftTrade.remove(id,_price);
            }
        }else if(this.price<_price){//兄弟节点
            if(this.rightTrade!=null)
            {
                this.rightTrade.remove(id,_price);
            }
        }else{
            if(this.siblingTrade!=null)
            {
                this.siblingTrade.remove(id,_price);
            }
        }
    }


    /**
     * 插入一个订单 在根节点的两侧各插入一个节点
     *
     * @param indexTrade
     */
    public synchronized void addIndexTrade(IndexTradeModel indexTrade) {
        if (indexTrade == null)
            return;
        IndexTradeTree tradeTree1 = new IndexTradeTree(indexTrade.getId(), indexTrade, indexTrade.getStopLoss());
        add(tradeTree1);
        IndexTradeTree tradeTree2 = new IndexTradeTree(indexTrade.getId(), indexTrade, indexTrade.getStopProfit());
        add(tradeTree2);
    }

    /**
     * 删除根节点后 将左右两个节点合并成一个新节点
     *
     * @param leftTree
     * @param rightTree
     * @return
     */
    public synchronized IndexTradeTree mergeTree(IndexTradeTree leftTree, IndexTradeTree rightTree) {
        if (leftTree == null)
            return rightTree;
        if (rightTree == null)
            return leftTree;
        //TODO
        return null;
    }

    /**
     * 子节点按照顺序插入到 list中
     *
     * @return
     */
    public List<IndexTradeModel> toList() {
        //TODO 改为非递归
        List<IndexTradeModel> list = new ArrayList<>();
        IndexTradeTree tradeTree = this;
        if (tradeTree != null) {
            //左中右  即前序遍历
            if (tradeTree.leftTrade != null) {
                list.addAll(tradeTree.leftTrade.toList());
            }
            //兄弟节点
            IndexTradeTree temp=tradeTree;
            while (temp!=null) {
                list.add(temp.indexTrade);
                temp=temp.siblingTrade;
            }
            if (tradeTree.rightTrade != null) {
                list.addAll(tradeTree.rightTrade.toList());
            }
        }
        return list;
    }

    /**
     * @return
     */
    public List<IndexTradeTree> toTreeList() {
        //TODO 改为非递归
        List<IndexTradeTree> list = new ArrayList<>();
        //左中右  即前序遍历
        if (this.leftTrade != null) {
            list.addAll(this.leftTrade.toTreeList());
            this.leftTrade.parentTrade = null;
        }
        list.add(this);
        if (this.rightTrade != null) {
            list.addAll(this.rightTrade.toTreeList());
            this.parentTrade = null;
        }
        this.leftTrade = null;
        this.rightTrade = null;
        return list;
    }

    /**
     * 得到需要平仓的订单
     *
     * @param _price
     * @return
     */
    public synchronized List<IndexTradeModel> getAbovePrice(double _price) {
        if(this.id==0)//根节点
        {
            if(this.rightTrade!=null) {
                return this.rightTrade.getAbovePrice(_price);
            }
        }
        if (this.price >=_price)//右节点全部需要平仓
        {
            IndexTradeTree parent=this.parentTrade;
            if(parent!=null) {
                parent.rightTrade = null;
            }
            return this.toList();
        } else  {
            //TODO 非递归
            if(this.rightTrade!=null) {
                return this.rightTrade.getAbovePrice(_price);
            }
        }
        return null;
    }

    /**
     * 得到需要平仓的订单
     *
     * @param _price
     * @return
     */
    public synchronized List<IndexTradeModel> getBelowPrice(double _price) {
        if(this.id==0)//根节点
        {
            if (this.leftTrade != null) {
                return this.leftTrade.getBelowPrice(_price);
            }
        }
        if (this.price <=_price) {
            IndexTradeTree parent=this.parentTrade;
            if(parent!=null) {
                parent.leftTrade = null;
            }
            return this.toList();
        } else {
            if (this.leftTrade != null) {
                return this.leftTrade.getBelowPrice(_price);
            }
        }
        return null;
    }

    /**
     * 根据一个列表来构建一棵树
     *
     * @param list
     * @return
     */
    public static IndexTradeTree getTreeFromList(List<IndexTradeTree> list) {
        //TODO 改为非递归
        if (list == null)
            return null;
        int s = list.size();
        if (s == 0)
            return null;
        if (s == 1) {
            return list.get(0);
        }
        int i = s / 2;
        //构建BST
        IndexTradeTree tradeTree = list.get(i);
        tradeTree.leftTrade = getTreeFromList(list.subList(0, i));
        tradeTree.rightTrade = getTreeFromList(list.subList(i + 1, s));
        return tradeTree;
    }

    /**
     * 根据所有订单构建一棵树
     *
     * @param list  未平仓的所有订单
     * @param _price 最新价格
     * @return 订单树
     */
    public static IndexTradeTree getTreeFromList(List<IndexTradeModel> list, double _price) {
        IndexTradeTree rootTree = new IndexTradeTree(0, null, _price);
        if (list == null || list.size() == 0)
            return rootTree;
        for (IndexTradeModel model : list) {
            rootTree.addIndexTrade(model);
        }
        return rootTree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexTradeTree tradeTree = (IndexTradeTree) o;

        if (id != tradeTree.id) return false;
        return Double.compare(tradeTree.price, price) == 0;

    }

    /**
     * 节点数量
     * @return
     */
    public int size()
    {
        return size;
    }
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    public void print()
    {

    }
    @Override
    public String toString() {
        return "(" +
                "" + id +
                "," + price +
                ')';
    }
}
