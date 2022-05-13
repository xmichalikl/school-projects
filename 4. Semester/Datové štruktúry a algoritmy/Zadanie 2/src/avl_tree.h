#ifndef AVL_TREE_H
#define AVL_TREE_H

typedef struct treedata { 
    char name[50];
    int age;
} TREEDATA;

typedef struct tree { 
    TREEDATA data;
    int height;
    struct tree *left;
    struct tree *right;
} TREE;

TREE *createNewLeaf(char *newName, int newAge);
int getLeafHeigh(TREE *leaf);
int calculateHeigh(TREE *leaf);
int calculateBalanceFactor(TREE *leaf);
TREE *rotateLeft(TREE *leaf);
TREE *rotateRight(TREE *leaf);
TREE *insertTree(TREE *tree, char *name, int age);
TREE *searchTree(TREE *tree, char *findName, int findAge);
void printTree(TREE* tree);
void deleteTree(TREE *tree);
void compare(char *string_1, char *string_2);
void freeTree(TREE **tree);

#endif