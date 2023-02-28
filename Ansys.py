import numpy as np

def create_user_hotel_matrix(users, items, data, hotel_id):
    '''
    构建用户-酒店矩阵
    :param users: 用户数量，类型为整数
    :param items: 酒店数量，类型为整数
    :param data: 原始数据，类型为DataFrame
    :param hotel_id: 酒店ID的列表，类型为列表
    :return: user_hotel_matrix
    '''
    user_hotel_matrix = np.zeros((users, items))
    for line in data.itertuples():
        user_hotel_matrix[line[3]][hotel_id.index(line[1])] = line[4]
    return user_hotel_matrix

class Matrix_Decomposition():
    def __init__(self,user_hotel_matrix,learning_rate = 1e-3, epoch = 100, tol = 0.1):
        self.matrix = user_hotel_matrix
        self.lr = learning_rate
        self.epoch = epoch
        self.tol = tol

    def MSE(self,m1,m2,record):

        m3 = np.dot(m1,m2)
        m4 = self.matrix - m3
        m4 = np.square(m4)
        loss = 0.5*sum(m4*record)
        return loss


    def train(self,feature_dim = 8):
        # feature_dim 用于调节矩阵分解的特征维数,超参
        m,n = self.matrix
        matrix_1 = np.random.uniform(0, 1, (m, feature_dim))
        matrix_2 = np.random.uniform(0, 1, (feature_dim, n))

        record = np.array(self.matrix > 0, dtype=int)

        for i in range(self.epoch):
            grad_1 = np.dot(np.multiply(record, np.dot(matrix_1,matrix_2)-self.matrix),matrix_2.T)
            grad_2 = np.dot(matrix_1.T, np.multiply(record, np.dot(matrix_1, matrix_2) - self.matrix))

            matrix_1 = matrix_1 - self.lr*grad_1
            matrix_2 = matrix_2 - self.lr*grad_2

            if self.MSE(matrix_1,matrix_2,record) < self.tol:
                break

        return np.dot(matrix_1,matrix_2).reshape((m,n))

    def recommend_hotel(self,id):
        A = self.train()
        ranklist = np.argsort(A[id])
        recommend = ranklist[-1:-4:-1]
        return recommend[-1], recommend[-2], recommend[-3]