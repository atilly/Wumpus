(define (domain wumpusworld)
(:requirements :adl :typing :strips :conditional-effects)

(:types 
	player arrow room sensor danger - object
	smell breeze - sensor
	wumpus pit - danger
)

(:predicates
	(has ?x)
	(adjacent ?x ?y)
	(at ?x ?y)
	(isAlive)
	(hasGold)
	(canShoot ?r1 ?r2)
	(wumpusAt ?r - room)
	(goldAt ?r - room)
	(pit ?r - room)
	(stench ?r - room)
	(breeze ?r - room)
)

(:action move
	:parameters (?p - player, ?from - room, ?to - room)
	:precondition (and 
						(and 
							(and (isAlive) (at ?p ?from)) 
							(and (adjacent ?from ?to) (not (wumpusAt ?to)))
						)
						(not (pit ?to)
						)
				)
	:effect (and (at ?p ?to) (not (at ?p ?from)))
)

(:action moveToPitRoom
	:parameters (?p - player, ?from - room, ?to - room)
	:precondition (and 
						(and 
							(and (isAlive) (at ?p ?from)) 
							(and (adjacent ?from ?to) (not (wumpusAt ?to)))
						)
						(pit ?to
						)
				)
	:effect (and 
				(and (not (isAlive)) (not (hasGold))) 
				(not (at ?p ?from))
			)
)

(:action moveToWumpusRoom
	:parameters (?p - player, ?from - room, ?to - room)
	:precondition (and 
						(and 
							(and (isAlive) (at ?p ?from)) 
							(and (adjacent ?from ?to) (wumpusAt ?to))
						)
						(not (pit ?to)
						)
				)
	:effect (and 
				(and (not (isAlive)) (not (hasGold))) 
				(not (at ?p ?from))
			)
)

(:action shoot
	:parameters (?p - player, ?a - arrow, ?from - room, ?to - room)
	:precondition (and 
					(and (isAlive) (canShoot ?from ?to)) 
					(and (has ?a) (at ?p ?from))
				)
	:effect (and 
				(not (has ?a)) 
				(not (wumpusAt ?to))
			)
)

(:action pickup
	:parameters (?r - room ?p - player)
	:precondition (and 
					(goldAt ?r) 
					(at ?p ?r)
				)
	:effect (and 
				(not (goldAt ?r)) 
				(hasGold)
			)
)

)
