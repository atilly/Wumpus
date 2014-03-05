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
	:parameters (?p - player ?origin - room ?dest - room)
	:precondition (and
					(adjacent ?origin ?dest) 
					(at ?p ?origin)
				)
	:effect (and
				(not(at ?p ?origin)) 
				(at ?p ?dest)
			)
)

(:action move
	:parameters (?p - player, ?from - room, ?to - room)
	:precondition (and 
					(and 
						(isAlive) 
						(at ?from ?p)
					) 
					(adjacent ?from ?to)
				)
	:effect (
		(when (wumpusAt ?to) 
		(not (isAlive)))

		(when (pitAt ?to) 
		(not (isAlive)))

		and (at ?p ?to) (not (at ?p ?from))
		)
)

(:action shoot
	:parameters (?p - player, ?a - arrow, ?from - room, ?to - room)
	:precondition (and 
					(and (isAlive) (canShoot ?from ?to)) 
					(has ?a)
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
