package com.leetcode;

class ListNode {
	int val;
	ListNode next;
	ListNode(int x) { val = x; }
	
	//添加节点
    public ListNode add(int newdata){
        ListNode newNode = new ListNode(newdata);
        if(this.next==null){
            this.next = newNode;
        }else{
            this.next.add(newdata);
        }
        return this.next;
    }
    //输出
    public void print(){
        System.out.print(this.val + "-->");
        if(this.next!=null){
            this.next.print();
        }
    }
    
    @Override
    public String toString() {
    	return String.valueOf(this.val);
    }
}