import numpy as np
import scipy.optimize as opt	
from math import sin, sqrt, cos
import random as rm
n = 2
iMax = 20
lRate = .02
prec = .0001

def schwefel(z):
	return 418.9829 * len(z) + reduce(lambda x,y: x+y, map(lambda x: -x*sin(sqrt(abs(x))), z) )

def schwefel2(z):
	return 418.9829 * len(z) + reduce(lambda x,y: x+y, map(lambda x: -x*sin(sqrt(abs(x))), z) )

def anneal():
	return opt.minimize(schwefel, (0,0), method = 'Anneal')

def min():
	return opt.minimize(schwefel, (200,200), bounds = ((-500, 500), (-500, 500))) 

def schwefel_der(z):
	res = list(-1*(sin(sqrt(abs(x))) + 0.5*sqrt(abs(x))*cos(sqrt(abs(x)))) for x in z)
	return res

def gradDescX():
	return gradDesc(10, lambda x: x*x, lambda x: 2*x)

def gradDescS():
	return gradDesc((350,350), schwefel, schwefel_der)

def gradDesc(tOld, f, f_der):
	i = 0
	tOld = np.array(tOld)
	while True:
		++i
		tNew = tOld - np.array(f_der(tOld)) * lRate
		print(tNew)
		if (np.linalg.norm(tNew - tOld) < prec):
			print("littleChange")
			break
		if (i > iMax):
			print("maxIter")
			break
		tOld = tNew
	print("iter: {}".format(i))
	return(tNew, f(tNew))

def desc(epochN, f, f_der):
	for x in range(epochN):
		minN = 





















from random import random

def anneal(solution):
    old_cost = cost(solution)
    T = 1.0
    T_min = 0.00001
    alpha = 0.9
    while T > T_min:
        i = 1
        while i <= 100:
            new_solution = neighbor(solution)
            new_cost = cost(new_solution)
            ap = acceptance_probability(old_cost, new_cost, T)
            if ap > random():
                solution = new_solution
                old_cost = new_cost
            i += 1
        T = T*alpha
    return solution, old_cost