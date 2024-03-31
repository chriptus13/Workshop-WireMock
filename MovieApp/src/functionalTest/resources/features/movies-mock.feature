Feature: Movies mock scenarios

  Background:
    Given reset WireMock mappings
    Given reset WireMock requests

  Scenario: Search for mocked movies works
    Given stub 'movie-search' has the following response body
    """
    {
      "page": 1,
      "total_results": 3,
      "results": [
        {
          "id": "118340",
          "original_title": "Guardians of the Galaxy",
          "release_date": "2014-07-30"
        },
        {
          "id": "283995",
          "original_title": "Guardians of the Galaxy Vol. 2",
          "release_date": "2017-04-19"
        },
        {
          "id": "447365",
          "original_title": "Guardians of the Galaxy Vol. 3",
          "release_date": "2023-05-03"
        }
      ]
    }
    """
    When I search for 'Guardians of the Galaxy'
    Then response status is 200
    And returned movies include
      | id     | title                          | releaseDate |
      | 118340 | Guardians of the Galaxy        | 2014-07-30  |
      | 283995 | Guardians of the Galaxy Vol. 2 | 2017-04-19  |
      | 447365 | Guardians of the Galaxy Vol. 3 | 2023-05-03  |

  Scenario: Movie details works
    Given stub 'movie' has the following response body
    """
    {
      "id": "1234",
      "imdb_id": "tt1234",
      "original_title": "My awesome movie",
      "overview": "The hilarious misadventures of André unfold as he attempts to unravel the mysteries of the infamous WireMock at KLx!",
      "release_date": "2024-04-02"
    }
    """
    Given stub 'movie-cast' has the following response body
    """
    {
      "id": "1234",
      "cast": [
        {
          "id": "5678",
          "name": "André Martins",
          "character": "André"
        }
      ]
    }
    """
    When I get movie with id '118340'
    Then response status is 200
    And returned movie has the following details
      | id   | title            | imdbId | releaseDate |
      | 1234 | My awesome movie | tt1234 | 2024-04-02  |
    And returned movie has the following overview
    """
    The hilarious misadventures of André unfold as he attempts to unravel the mysteries of the infamous WireMock at KLx!
    """
    And returned movie includes the following cast
      | id   | name          | character |
      | 5678 | André Martins | André     |

  Scenario: Movie details for non existent movie returns not found
    Given stub 'movie' has response status of 404
    When I get movie with id '118340'
    Then response status is 404
