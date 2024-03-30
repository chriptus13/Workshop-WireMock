Feature: Movies real scenarios

  Scenario: Search for movies works
    When I search for 'Guardians of the Galaxy'
    Then response status is 200
    And returned movies include
      | id     | title                          | releaseDate |
      | 118340 | Guardians of the Galaxy        | 2014-07-30  |
      | 283995 | Guardians of the Galaxy Vol. 2 | 2017-04-19  |
      | 447365 | Guardians of the Galaxy Vol. 3 | 2023-05-03  |

  Scenario: Movie details works for existent movie
    When I get movie with id '118340'
    Then response status is 200
    And returned movie has the following details
      | id     | title                   | imdbId    | releaseDate |
      | 118340 | Guardians of the Galaxy | tt2015381 | 2014-07-30  |
    And returned movie has the following overview
    """
    Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser.
    """
    And returned movie includes the following cast
      | id     | name           | character               |
      | 73457  | Chris Pratt    | Peter Quill / Star-Lord |
      | 8691   | Zoe Saldaña    | Gamora                  |
      | 543530 | Dave Bautista  | Drax the Destroyer      |
      | 12835  | Vin Diesel     | Groot (voice)           |
      | 51329  | Bradley Cooper | Rocket (voice)          |

  Scenario: Movie details for non existent movie returns not found
    When I get movie with id '1234567890'
    Then response status is 404
