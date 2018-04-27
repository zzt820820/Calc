package com.example.calc;

import java.util.Stack;

/**
 * ������
 */
public class Calculator {

    /** ����ջ�����ڴ洢���ʽ�еĸ������� */
    private Stack<Long> numberStack = null;
    /** ����ջ�����ڴ洢����������� */
    private Stack<Character> symbolStack = null;

    /**
     * ��������������������ʽ(������)�����ؼ�����
     * 
     * @param numStr
     *            �������ʽ(������)
     */
    public long caculate(String numStr) {
        numStr = removeStrSpace(numStr); // ȥ���ո�
        // ����������ʽβ��û�С�=���ţ�����β����ӡ�=������ʾ������
        if (numStr.length() > 1 && !"=".equals(numStr.charAt(numStr.length() - 1) + "")) {
            numStr += "=";
        }
        // �����ʽ�Ƿ�Ϸ�
        if (!isStandard(numStr)) {
            System.err.println("�����������ʽ����");
            return 0;
        }
        // ��ʼ��ջ
        numberStack = new Stack<Long>();
        symbolStack = new Stack<Character>();
        // ���ڻ������֣���Ϊ���ֿ����Ƕ�λ��
        StringBuffer temp = new StringBuffer();
        // �ӱ��ʽ�ĵ�һ���ַ���ʼ����
        for (int i = 0; i < numStr.length(); i++) {
            char ch = numStr.charAt(i); // ��ȡһ���ַ�
            if (isNumber(ch)) { // ����ǰ�ַ�������
                temp.append(ch); // ���뵽���ֻ�����
            } else { // �����ֵ����
                String tempStr = temp.toString(); // �����ֻ���תΪ�ַ���
                if (!tempStr.isEmpty()) {
                    long num = Long.parseLong(tempStr); // �������ַ���תΪ��������
                    numberStack.push(num); // ������ѹջ
                    temp = new StringBuffer(); // �������ֻ���
                }
                // �ж�����������ȼ�������ǰ���ȼ�����ջ�������ȼ������ȰѼ���ǰ��������
                while (!comparePri(ch) && !symbolStack.empty()) {
                    long b = numberStack.pop(); // ��ջ��ȡ�����֣�����ȳ�
                    long a = numberStack.pop();
                    // ȡ�������������Ӧ���㣬���ѽ��ѹջ������һ������
                    switch ((char) symbolStack.pop()) {
                    case '+':
                        numberStack.push(a + b);
                        break;
                    case '-':
                        numberStack.push(a - b);
                        break;
                    case 'X':
                        numberStack.push(a * b);
                        break;
                    case '/':
                        numberStack.push(a / b);
                        break;
                    default:
                        break;
                    }
                } // whileѭ������
                if (ch != '=') {
                    symbolStack.push(new Character(ch)); // ������ջ
                    if (ch == ')') { // ȥ����
                        symbolStack.pop();
                        symbolStack.pop();
                    }
                }
            }
        } // forѭ������

        return numberStack.pop(); // ���ؼ�����
    }

    /**
     * ȥ���ַ����е����пո�
     */
    private String removeStrSpace(String str) {
        return str != null ? str.replaceAll(" ", "") : "";
    }

    /**
     * ����������ʽ�Ļ����Ϸ��ԣ����Ϸ���true������false
     */
    private boolean isStandard(String numStr) {
        if (numStr == null || numStr.isEmpty()) // ���ʽ����Ϊ��
            return false;
        Stack<Character> stack = new Stack<Character>(); // �����������ţ�������������Ƿ�ƥ��
        boolean b = false; // �������'='�����Ƿ���ڶ��
        for (int i = 0; i < numStr.length(); i++) {
            char n = numStr.charAt(i);
            // �ж��ַ��Ƿ�Ϸ�
            if (!(isNumber(n) || "(".equals(n + "") || ")".equals(n + "")
                    || "+".equals(n + "") || "-".equals(n + "")
                    || "X".equals(n + "") || "/".equals(n + "")
                    || "=".equals(n + ""))) {
                return false;
            }
            // ��������ѹջ������������������Ž���ƥ��
            if ("(".equals(n + "")) {
                stack.push(n);
            }
            if (")".equals(n + "")) { // ƥ������
                if (stack.isEmpty() || !"(".equals((char) stack.pop() + "")) // �����Ƿ�ƥ��
                    return false;
            }
            // ����Ƿ��ж��'='��
            if ("=".equals(n + "")) {
                if (b)
                    return false;
                b = true;
            }
        }
        // ���ܻ���ȱ�������ŵ����
        if (!stack.isEmpty())
            return false;
        // ���'='���Ƿ���ĩβ
        if (!("=".equals(numStr.charAt(numStr.length() - 1) + "")))
            return false;
        return true;
    }

    /**
     * �ж��ַ��Ƿ���0-9������
     */
    private boolean isNumber(char num) {
        if (num >= '0' && num <= '9')
            return true;
        return false;
    }

    /**
     * �Ƚ����ȼ��������ǰ�������ջ��Ԫ����������ȼ����򷵻�true�����򷵻�false
     */
    private boolean comparePri(char symbol) {
        if (symbolStack.empty()) { // ��ջ����ture
            return true;
        }

        // �������ȼ�˵�����Ӹߵ��ͣ�:
        // ��1��: (
        // ��2��: * /
        // ��3��: + -
        // ��4��: )

        char top = (char) symbolStack.peek(); // �鿴��ջ�����Ķ���ע�ⲻ�ǳ�ջ
        if (top == '(') {
            return true;
        }
        // �Ƚ����ȼ�
        switch (symbol) { 
        case '(': // ���ȼ����
            return true;
        case 'X': {
            if (top == '+' || top == '-') // ���ȼ���+��-��
                return true;
            else
                return false;
        }
        case '/': {
            if (top == '+' || top == '-') // ���ȼ���+��-��
                return true;
            else
                return false;
        }
        case '+':
            return false;
        case '-':
            return false;
        case ')': // ���ȼ����
            return false;
        case '=': // ������
            return false;
        default:
            break;
        }
        return true;
    }
}