package com.sczfdf.maa.random.dice.bean;

import lombok.Data;

import java.util.List;

/**
 * @author cgb
 */
@Data
public class Node {
    public Dice val;
    public Node next;

    public Node(Dice val) {
        this.val = val;
    }

    public static Node listToCircularLinkedList(List<Dice> diceList) {
        if (diceList == null || diceList.isEmpty()) {
            return null;
        }

        Node head = new Node(diceList.get(0));
        Node current = head;

        for (int i = 1; i < diceList.size(); i++) {
            current.next = new Node(diceList.get(i));
            current = current.next;
        }

        // Make it circular
        current.next = head;

        return head;
    }
}