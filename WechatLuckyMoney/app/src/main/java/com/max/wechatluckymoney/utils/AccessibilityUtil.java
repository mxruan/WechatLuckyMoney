package com.max.wechatluckymoney.utils;

import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;


/**
 * Created by Max on 2018/2/10.
 * 辅助服务 工具类
 */

public class AccessibilityUtil
{

    /**
     * 获取一个 node 根据 文本
     *
     * @param info  节点
     * @param texts 需要匹配的文字
     */
    public static AccessibilityNodeInfo getOneNodeByText(AccessibilityNodeInfo info, String... texts)
    {
        if (info != null)
        {
            if (info.getChildCount() == 0)
            {
                if (info.getText() != null)
                {
                    String text = info.getText().toString();

                    for (String str : texts)
                    {
                        if (text.contains(str))
                        {
                            return info;
                        }
                    }
                }
            } else
            {
                int size = info.getChildCount();
                for (int i = 0; i < size; i++)
                {
                    AccessibilityNodeInfo childInfo = info.getChild(i);
                    if (childInfo != null)
                    {
                        AccessibilityNodeInfo nodeInfo = getOneNodeByText(childInfo, texts);
                        if (nodeInfo != null)
                        {
                            return nodeInfo;
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * 是否有 指定文本的Node
     *
     * @param info  当前节点
     * @param texts 需要匹配的文字
     */
    public static boolean hasOneNodeByText(AccessibilityNodeInfo info, String... texts)
    {
        if (info != null)
        {
            if (info.getChildCount() == 0)
            {
                if (info.getText() != null)
                {
                    String text = info.getText().toString();

                    for (String str : texts)
                    {
                        if (text.contains(str))
                        {
                            return true;
                        }
                    }
                }
            } else
            {
                int size = info.getChildCount();
                for (int i = 0; i < size; i++)
                {
                    AccessibilityNodeInfo childInfo = info.getChild(i);
                    if (childInfo != null)
                    {
                        hasOneNodeByText(childInfo, texts);
                    }
                }
            }
        }
        return false;
    }


    /**
     * 获取一个 node  根据 控件
     *
     * @param node
     * @param viewTag view 包名
     * @return
     */
    public static AccessibilityNodeInfo getOneNodeByViewTag(AccessibilityNodeInfo node, String viewTag)
    {
        if (node == null || viewTag == null)
        {
            return null;
        }
        //非layout元素
        if (node.getChildCount() == 0)
        {
            if (viewTag.equals(node.getClassName()))
            {
                return node;
            } else
            {
                return null;
            }
        }

        //layout元素，遍历找button
        AccessibilityNodeInfo viewNode;
        for (int i = 0; i < node.getChildCount(); i++)
        {
            viewNode = getOneNodeByViewTag(node.getChild(i), viewTag);
            if (viewNode != null)
            {
                return viewNode;
            }
        }
        return null;
    }


    /**
     * 打印 所有子node
     */
    public static void printAllNodeInfo(AccessibilityNodeInfo nodeInfo)
    {
        if (nodeInfo == null)
        {
            return;
        }

        if (nodeInfo.getChildCount() == 0)
        {
            printNodeInfo(nodeInfo);
        } else
        {
            for (int i = 0; i < nodeInfo.getChildCount(); i++)
            {
                AccessibilityNodeInfo childNode = nodeInfo.getChild(i);
                printAllNodeInfo(childNode);
            }
        }
    }

    /**
     * 打印 AccessibilityNodeInfo
     *
     * @param nodeInfo
     */
    public static void printNodeInfo(AccessibilityNodeInfo nodeInfo)
    {
        if (nodeInfo == null)
        {
            return;
        }

        L.e("printNodeInfo | ClassName > " + nodeInfo.getClassName().toString());
        L.e("printNodeInfo | ChildCount > " + nodeInfo.getChildCount());
        if (nodeInfo.getContentDescription() != null)
        {
            L.e("printNodeInfo | Description > " + nodeInfo.getContentDescription().toString());
        }
        if (nodeInfo.getText() != null)
        {
            L.e("printNodeInfo | Text > " + nodeInfo.getText().toString());
        }
    }
}
